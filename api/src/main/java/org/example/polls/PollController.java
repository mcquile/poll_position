package org.example.polls;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.polls.models.Poll;
import org.example.polls.models.dto.PollDTO;
import org.example.users.UserRepository;
import org.example.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.example.config.SecurityConfiguration.SECURITY_CONFIG_NAME;

@RestController
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequestMapping("/api/v1/polls")
public class PollController {

    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    public PollController(PollRepository pollRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<Poll>> getAllPolls(){
        return ResponseEntity.status(HttpStatus.OK).body(pollRepository.findAll());
    }

    @PostMapping
    void addPoll(@RequestBody PollDTO pollDTO){
        Poll poll = new Poll();
        poll.setPollCreationTime(Timestamp.from(Instant.now()));
        poll.setTitle(pollDTO.title());
        poll.setDescription(pollDTO.description());
        poll.setVoteEnd(pollDTO.voteEnd());
        poll.setVoteStart(pollDTO.voteStart());
        poll.setNominationEndTime(pollDTO.nominationEnd());

        User user = userRepository.findByUserId(pollDTO.pollCreatorID());
        poll.setPollCreator(user);
        pollRepository.save(poll);
    }
}
