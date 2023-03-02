package org.example.polls;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.branches.BranchRepository;
import org.example.exceptions.NoAuthorisationHeaderException;
import org.example.nominations.NominationRepository;
import org.example.nominations.NominationService;
import org.example.nominations.models.Nomination;
import org.example.nominations.models.dto.NominationDTO;
import org.example.polls.models.Poll;
import org.example.polls.models.dto.PollDTO;
import org.example.sexes.SexRepository;
import org.example.users.UserRepository;
import org.example.users.UserService;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.example.config.SecurityConfiguration.SECURITY_CONFIG_NAME;

@RestController
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequestMapping("/api/v1/polls")
@CrossOrigin
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

    @GetMapping
    ResponseEntity<Object> getAllPolls(Authentication authentication) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Stream<Poll> allPolls = pollRepository.findAllBy().stream();
        Stream<Poll> allowedPolls = allPolls.filter(poll -> PollService.isUserAllowedToVote(poll, user) || poll.getPollCreator() == user);
        Stream<Map<String, Object>> response = allowedPolls.map(PollService::convertPollIntoJSONResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    ResponseEntity<Object> addPoll(Authentication authentication, @RequestBody PollDTO pollDTO) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.convertFromDTO(pollDTO, user, userRepository, branchRepository, sexRepository);
        pollRepository.save(poll);
        return ResponseEntity.created(URI.create("./" + poll.getPollId())).body(PollService.convertPollIntoJSONResponse(poll));
    }

    @GetMapping("/{pollID}")
    ResponseEntity<Object> getPoll(Authentication authentication, @PathVariable UUID pollID) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.getPollByID(pollID, pollRepository);
        if (!PollService.isUserAllowedToVote(poll, user) && poll.getPollCreator() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        return ResponseEntity.ok(PollService.convertPollIntoJSONResponse(poll));
    }

    @GetMapping("/{pollID}/nominations")
    ResponseEntity<Object> getNominations(Authentication authentication, @PathVariable UUID pollID) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.getPollByID(pollID, pollRepository);
        if (!PollService.isUserAllowedToVote(poll, user) && poll.getPollCreator() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        return ResponseEntity.ok(poll.getNominations().stream().map(NominationService::convertNominationIntoJSONResponse));
    }


    @PostMapping("/{pollID}/nominations")
    ResponseEntity<Nomination> addNomination(Authentication authentication, @PathVariable UUID pollID, @RequestBody NominationDTO nominationDTO) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.getPollByID(pollID, pollRepository);
        if (!PollService.isUserAllowedToVote(poll, user) && poll.getPollCreator() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        if (poll.getNominationEndTime().before(Timestamp.from(Instant.now()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nomination Time has ended");
        }
        Nomination nomination = NominationService.createNominationFromDTO(nominationDTO, user, poll);
        nominationRepository.save(nomination);
        return ResponseEntity.created(URI.create("/" + nomination.getNominationId())).body(nomination);
    }

    @GetMapping("/{pollID}/nominations/{nominationID}")
    ResponseEntity<Nomination> getNomination(Authentication authentication, @PathVariable UUID pollID, @PathVariable Long nominationID) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.getPollByID(pollID, pollRepository);
        if (!PollService.isUserAllowedToVote(poll, user) && poll.getPollCreator() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        return ResponseEntity.ok(NominationService.getNominationByID(nominationID, nominationRepository));
    }

    @PostMapping("/{pollID}/nominations/{nominationID}/vote")
    ResponseEntity<UserVote> vote(Authentication authentication, @PathVariable UUID pollID, @PathVariable Long nominationID) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.getPollByID(pollID, pollRepository);
        Nomination nomination = NominationService.getNominationByID(nominationID, nominationRepository);
        if (!PollService.isUserAllowedToVote(poll, user) && poll.getPollCreator() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed Access to Poll");
        }
        if (poll.getVoteStart().after(Timestamp.from(Instant.now()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Voting has not yet started");
        }
        if (poll.getVoteEnd().before(Timestamp.from(Instant.now()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Voting has ended");
        }
        UserVote vote = new UserVote();
        vote.setUser(user);
        vote.setNomination(nomination);
        userVoteRepository.save(vote);
        return ResponseEntity.created(URI.create("./%d".formatted(vote.getUserVote()))).body(vote);
    }

    @DeleteMapping("/{pollID}")
    ResponseEntity<Object> deletePoll(Authentication authentication, @PathVariable UUID pollID) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.getPollByID(pollID, pollRepository);
        if (poll.getPollCreator() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot Delete someone else's poll");
        }
        pollRepository.delete(poll);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{pollID}/nominations/{nominationID}")
    ResponseEntity<Object> deleteNomination(Authentication authentication, @PathVariable UUID pollID, @PathVariable Long nominationID) throws NoAuthorisationHeaderException {
        User user = UserService.getUserFromAuthentication(authentication, userRepository);
        Poll poll = PollService.getPollByID(pollID, pollRepository);
        Nomination nomination = NominationService.getNominationByID(nominationID, nominationRepository);
        if (poll.getPollCreator() != user && nomination.getNominator() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot Delete someone else's nomination");
        }
        nominationRepository.delete(nomination);
        return ResponseEntity.ok().build();
    }
}
