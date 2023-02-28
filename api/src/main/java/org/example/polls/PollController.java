package org.example.polls;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.branches.BranchRepository;
import org.example.branches.models.Branch;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public PollController(PollRepository pollRepository, UserRepository userRepository, BranchRepository branchRepository, SexRepository sexRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.sexRepository = sexRepository;
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
                SpecificUserRestriction restriction = new SpecificUserRestriction();
                restriction.setPoll(poll);
                restriction.setRestricted(restrictionDTO.restricted());
                restriction.setUser(getUserFromEmail(restrictionDTO.email()));
                restrictions.add(restriction);
            }
            poll.setSpecificUserRestrictions(restrictions);
        }
        if(pollDTO.genericRestrictions().isPresent()) {
            List<UserRestriction> restrictions = new ArrayList<>();
            for (UserRestrictionDTO restrictionDTO : pollDTO.genericRestrictions().get()) {
                UserRestriction restriction = new UserRestriction();
                restriction.setPoll(poll);
                if(restrictionDTO.branchName().isPresent()){
                    Optional<Branch> branch = branchRepository.getBranchByBranchName(restrictionDTO.branchName().get());
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
                restrictions.add(restriction);
            }
            poll.setUserRestrictions(restrictions);
        }
        pollRepository.save(poll);
    }
}
