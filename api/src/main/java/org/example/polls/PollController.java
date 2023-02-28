package org.example.polls;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.branches.BranchRepository;
import org.example.branches.models.Branch;
import org.example.nominations.NominationRepository;
import org.example.nominations.models.Nomination;
import org.example.nominations.models.dto.NominationDTO;
import org.example.sexes.SexRepository;
import org.example.sexes.models.Sex;
import org.example.restrictions.models.SpecificUserRestriction;
import org.example.restrictions.models.UserRestriction;
import org.example.exceptions.NoAuthorisationHeaderException;
import org.example.polls.models.Poll;
import org.example.polls.models.dto.PollDTO;
import org.example.restrictions.models.dto.SpecificUserRestrictionDTO;
import org.example.restrictions.models.dto.UserRestrictionDTO;
import org.example.users.UserRepository;
import org.example.users.models.User;
import org.example.votes.UserVoteRepository;
import org.example.votes.models.UserVote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.example.config.SecurityConfiguration.SECURITY_CONFIG_NAME;

@RestController
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequestMapping("/api/v1/polls")
public class PollController {

    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    private final BranchRepository branchRepository;

    private final SexRepository sexRepository;
    private final NominationRepository nominationRepository;
    private final UserVoteRepository userVoteRepository;

    public PollController(PollRepository pollRepository, UserRepository userRepository, BranchRepository branchRepository, SexRepository sexRepository, NominationRepository nominationRepository, UserVoteRepository userVoteRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.sexRepository = sexRepository;
        this.nominationRepository = nominationRepository;
        this.userVoteRepository = userVoteRepository;
    }

    private boolean isUserAllowedToVote(Poll poll, User user){
        boolean userSpecificallyBanned = false;
        boolean userSpecificallyAllowed = false;
        for(SpecificUserRestriction restriction : poll.getSpecificUserRestrictions()){
            if(restriction.getUser()==user){
                userSpecificallyBanned = restriction.getRestricted();
                userSpecificallyAllowed = !restriction.getRestricted();
            }
        }
        if(userSpecificallyAllowed){
            return true;
        }
        if(userSpecificallyBanned){
            return false;
        }
        for(UserRestriction restriction: poll.getUserRestrictions()){
            if(userMatchesTemplate(user,restriction)){
                return true;
            }
        }
        return poll.getUserRestrictions().isEmpty() && poll.getSpecificUserRestrictions().isEmpty();
    }

    private boolean userMatchesTemplate(User user, UserRestriction template){
        boolean matchesTemplate;
        Branch branchRestriction = template.getBranchRestriction();
        Sex sexRestriction = template.getSexRestrictedTo();
        String firstNamePatternRestriction = template.getFirstNamePattern();
        String lastNamePatternRestriction = template.getLastNamePattern();
        Date dateOlderRestriction = template.getDateOfBirthOlder();
        Date dateYoungerRestriction = template.getDateOfBirthYounger();
        matchesTemplate = branchRestriction == null || branchRestriction == user.getBranch();
        matchesTemplate = matchesTemplate && (sexRestriction == null || sexRestriction == user.getSex());
        if(firstNamePatternRestriction != null){
            Pattern firstNamePattern = Pattern.compile(firstNamePatternRestriction,Pattern.CASE_INSENSITIVE);
            matchesTemplate = matchesTemplate && firstNamePattern.matcher(user.getFirstName()).matches();
        }
        if(lastNamePatternRestriction != null){
            Pattern lastNamePattern = Pattern.compile(lastNamePatternRestriction,Pattern.CASE_INSENSITIVE);
            matchesTemplate = matchesTemplate && lastNamePattern.matcher(user.getFirstName()).matches();
        }
        matchesTemplate = matchesTemplate && ( dateOlderRestriction == null ||dateOlderRestriction.after(user.getDateOfBirth()));
        matchesTemplate = matchesTemplate && (dateYoungerRestriction == null || dateYoungerRestriction.before(user.getDateOfBirth()));

        return matchesTemplate;
    }

    private User getUserFromAuthentication(Authentication authentication) throws NoAuthorisationHeaderException {
        String email = authentication.getName();
        try{
            return getUserFromEmail(email);
        }catch (ResponseStatusException e){
            throw new NoAuthorisationHeaderException();
        }
    }

    private User getUserFromEmail(String email) throws ResponseStatusException{
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No User with email: "+email);
        }
        return user.get();
    }

    @GetMapping
    ResponseEntity<Stream<Poll>> getAllPolls(Authentication authentication) throws NoAuthorisationHeaderException {
        User user = getUserFromAuthentication(authentication);
        Stream<Poll> allPolls = pollRepository.findAllBy().stream();
        Stream<Poll> allowedPolls = allPolls.filter(poll -> isUserAllowedToVote(poll,user) || poll.getPollCreator()==user);
        return ResponseEntity.status(HttpStatus.OK).body(allowedPolls);
    }

    @PostMapping
    void addPoll(Authentication authentication,@RequestBody PollDTO pollDTO) throws NoAuthorisationHeaderException {
        User user = getUserFromAuthentication(authentication);
        Poll poll = new Poll();
        poll.setPollCreator(user);
        poll.setPollCreationTime(Timestamp.from(Instant.now()));
        poll.setTitle(pollDTO.title());
        poll.setDescription(pollDTO.description());
        poll.setVoteEnd(pollDTO.voteEnd());
        poll.setVoteStart(pollDTO.voteStart());
        poll.setNominationEndTime(pollDTO.nominationEnd());
        if(pollDTO.specificUserRestrictions().isPresent()){
            List<SpecificUserRestriction> restrictions = new ArrayList<>();
            for(SpecificUserRestrictionDTO restrictionDTO : pollDTO.specificUserRestrictions().get()){
                restrictions.add(createSpecificRestrictionFromDTO(restrictionDTO,poll));
            }
            poll.setSpecificUserRestrictions(restrictions);
        }
        if(pollDTO.genericRestrictions().isPresent()) {
            List<UserRestriction> restrictions = new ArrayList<>();
            for (UserRestrictionDTO restrictionDTO : pollDTO.genericRestrictions().get()) {
                restrictions.add(createGenericRestrictionFromDTO(restrictionDTO,poll));
            }
            poll.setUserRestrictions(restrictions);
        }
        pollRepository.save(poll);
    }

    private SpecificUserRestriction createSpecificRestrictionFromDTO(SpecificUserRestrictionDTO restrictionDTO, Poll poll){
        SpecificUserRestriction restriction = new SpecificUserRestriction();
        restriction.setPoll(poll);
        restriction.setRestricted(restrictionDTO.restricted());
        restriction.setUser(getUserFromEmail(restrictionDTO.email()));
        return restriction;
    }

    private UserRestriction createGenericRestrictionFromDTO(UserRestrictionDTO restrictionDTO, Poll poll){

        UserRestriction restriction = new UserRestriction();
        restriction.setPoll(poll);
        if(restrictionDTO.branchName().isPresent()){
            Optional<Branch> branch = branchRepository.findBranchByBranchNameEqualsIgnoreCase(restrictionDTO.branchName().get());
            if(branch.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unknown Branch name: "+restrictionDTO.branchName().get());
            }
            restriction.setBranchRestriction(branch.get());
        }
        if(restrictionDTO.male().isPresent()){
            Sex sex = sexRepository.getSexBySexId(restrictionDTO.male().get());
            restriction.setSexRestrictedTo(sex);
        }
        if(restrictionDTO.firstNamePattern().isPresent()){
            restriction.setFirstNamePattern(restrictionDTO.firstNamePattern().get());
        }
        if(restrictionDTO.lastNamePattern().isPresent()){
            restriction.setLastNamePattern(restrictionDTO.lastNamePattern().get());
        }
        if(restrictionDTO.olderThan().isPresent()){
            restriction.setDateOfBirthOlder(restrictionDTO.olderThan().get());
        }
        if(restrictionDTO.youngerThan().isPresent()){
            restriction.setDateOfBirthYounger(restrictionDTO.youngerThan().get());
        }
        return restriction;
    }

    private Poll getPollByID(UUID pollID){
        Optional<Poll> poll = pollRepository.findById(pollID);
        if(poll.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Poll found with ID: "+pollID);
        }
        return poll.get();
    }


    @GetMapping("/{pollID}/nominations")
    List<Nomination> getNominations(Authentication authentication, @PathVariable UUID pollID) throws NoAuthorisationHeaderException {
        User user = getUserFromAuthentication(authentication);
        Poll poll = getPollByID(pollID);
        if(!isUserAllowedToVote(poll,user) && poll.getPollCreator()!=user){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        return poll.getNominations();
    }


    Nomination createNominationFromDTO(NominationDTO nominationDTO, User nominator, Poll poll){
        Nomination nomination = new Nomination();
        nomination.setNominee(nominationDTO.nominee());
        nomination.setNominator(nominator);
        nomination.setPoll(poll);
        return nomination;
    }


    @PostMapping("/{pollID}/nominations")
    ResponseEntity<Nomination> addNomination(Authentication authentication, @PathVariable UUID pollID, @RequestBody NominationDTO nominationDTO) throws NoAuthorisationHeaderException {
        User user = getUserFromAuthentication(authentication);
        Poll poll = getPollByID(pollID);
        if(!isUserAllowedToVote(poll,user) && poll.getPollCreator()!=user){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        if(poll.getNominationEndTime().before(Timestamp.from(Instant.now()))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nomination Time has ended");
        }
        Nomination nomination = createNominationFromDTO(nominationDTO, user, poll);
        nominationRepository.save(nomination);
        return ResponseEntity.created(URI.create("/"+nomination.getNominationId())).body(nomination);
    }

    private Nomination getNominationByID(Long nominationID) throws ResponseStatusException{
        Optional<Nomination> nomination = nominationRepository.findById(nominationID);
        if(nomination.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Nomination found with ID: "+nominationID);
        }
        return nomination.get();
    }

    @GetMapping("/{pollID}/nominations/{nominationID}")
    ResponseEntity<Nomination> getNomination(Authentication authentication, @PathVariable UUID pollID, @PathVariable Long nominationID) throws NoAuthorisationHeaderException {
        User user = getUserFromAuthentication(authentication);
        Poll poll = getPollByID(pollID);
        if(!isUserAllowedToVote(poll,user) && poll.getPollCreator()!=user){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        return ResponseEntity.ok(getNominationByID(nominationID));
    }

    @PostMapping("/{pollID}/nominations/{nominationID}/vote")
    ResponseEntity<UserVote> vote(Authentication authentication,@PathVariable UUID pollID, @PathVariable Long nominationID) throws NoAuthorisationHeaderException {
        User user = getUserFromAuthentication(authentication);
        Poll poll = getPollByID(pollID);
        Nomination nomination = getNominationByID(nominationID);
        if(!isUserAllowedToVote(poll,user) && poll.getPollCreator()!=user){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        if(poll.getVoteStart().after(Timestamp.from(Instant.now()))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Voting has not yet started");
        }
        if(poll.getVoteEnd().before(Timestamp.from(Instant.now()))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Voting has ended");
        }
        UserVote vote = new UserVote();
        vote.setUser(user);
        vote.setNomination(nomination);
        userVoteRepository.save(vote);
        return ResponseEntity.created(URI.create("./%d".formatted(vote.getUserVote()))).body(vote);
    }
}
