package org.example.polls;

import org.example.branches.BranchRepository;
import org.example.nominations.NominationService;
import org.example.nominations.models.Nomination;
import org.example.polls.models.Poll;
import org.example.polls.models.dto.PollDTO;
import org.example.restrictions.RestrictionsService;
import org.example.restrictions.models.SpecificUserRestriction;
import org.example.restrictions.models.UserRestriction;
import org.example.restrictions.models.dto.SpecificUserRestrictionDTO;
import org.example.restrictions.models.dto.UserRestrictionDTO;
import org.example.sexes.SexRepository;
import org.example.users.UserRepository;
import org.example.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class PollService {
    private PollService(){

    }

    public static Poll convertFromDTO(PollDTO pollDTO, User user, UserRepository userRepository, BranchRepository branchRepository, SexRepository sexRepository){

        Poll poll = new Poll();
        poll.setPollCreator(user);
        poll.setPollCreationTime(Timestamp.from(Instant.now()));
        poll.setTitle(pollDTO.title());
        poll.setDescription(pollDTO.description());
        poll.setVoteEnd(pollDTO.voteEnd());
        poll.setVoteStart(pollDTO.voteStart());
        poll.setNominationEndTime(pollDTO.nominationEnd());
        if (pollDTO.specificUserRestrictions().isPresent()) {
            List<SpecificUserRestriction> restrictions = new ArrayList<>();
            for (SpecificUserRestrictionDTO restrictionDTO : pollDTO.specificUserRestrictions().get()) {
                restrictions.add(RestrictionsService.createSpecificRestrictionFromDTO(restrictionDTO, poll,userRepository));
            }
            poll.setSpecificUserRestrictions(restrictions);
        }
        if (pollDTO.genericRestrictions().isPresent()) {
            List<UserRestriction> restrictions = new ArrayList<>();
            for (UserRestrictionDTO restrictionDTO : pollDTO.genericRestrictions().get()) {
                restrictions.add(RestrictionsService.createGenericRestrictionFromDTO(restrictionDTO, poll, branchRepository, sexRepository));
            }
            poll.setUserRestrictions(restrictions);
        }
        if(pollDTO.nominations().isPresent()){
            poll.setNominations(pollDTO.nominations().get().stream().map(nominationDTO -> {
                Nomination nomination = new Nomination();
                nomination.setNominee(nominationDTO.nominee());
                nomination.setNominator(user);
                return nomination;
            }).toList());
        }
        return poll;
    }

    public static boolean isUserAllowedToVote(Poll poll, User user) {
        boolean userSpecificallyBanned = false;
        boolean userSpecificallyAllowed = false;
        for (SpecificUserRestriction restriction : poll.getSpecificUserRestrictions()) {
            if (restriction.getUser() == user) {
                userSpecificallyBanned = restriction.getRestricted();
                userSpecificallyAllowed = !restriction.getRestricted();
            }
        }
        if (userSpecificallyAllowed) {
            return true;
        }
        if (userSpecificallyBanned) {
            return false;
        }
        for (UserRestriction restriction : poll.getUserRestrictions()) {
            if (RestrictionsService.userMatchesGenericRestriction(user, restriction)) {
                return true;
            }
        }
        return poll.getUserRestrictions().isEmpty() && poll.getSpecificUserRestrictions().isEmpty();
    }


    public static Map<String,Object> convertPollIntoJSONResponse(Poll poll){

        Map<String,Object> pollResponse = new HashMap<>();
        pollResponse.put("id",poll.getPollId());
        pollResponse.put("title",poll.getTitle());
        pollResponse.put("description",poll.getDescription());
        pollResponse.put("voteStart",poll.getVoteStart());
        pollResponse.put("voteEnd",poll.getVoteEnd());
        pollResponse.put("nominationEndTime",poll.getNominationEndTime());
        pollResponse.put("creationTime",poll.getPollCreationTime());
        {
            Map<String,Object> pollCreator = new HashMap<>();
            pollCreator.put("firstName",poll.getPollCreator().getFirstName());
            pollCreator.put("lastName",poll.getPollCreator().getLastName());
            pollCreator.put("email",poll.getPollCreator().getEmail());
            pollResponse.put("creator",pollCreator);
        }
        pollResponse.put("nominations",poll.getNominations().stream().map(NominationService::convertNominationIntoJSONResponse));
        return pollResponse;
    }

    public static Poll getPollByID(UUID pollID, PollRepository pollRepository) throws ResponseStatusException{
        Optional<Poll> poll = pollRepository.findById(pollID);
        if (poll.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Poll found with ID: " + pollID);
        }
        return poll.get();
    }

}
