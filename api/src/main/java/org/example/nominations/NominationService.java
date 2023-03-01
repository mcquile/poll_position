package org.example.nominations;

import org.example.nominations.models.Nomination;
import org.example.nominations.models.dto.NominationDTO;
import org.example.polls.models.Poll;
import org.example.users.models.User;
import org.example.votes.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NominationService {

    private NominationService(){

    }

    public static Map<String,Object> convertNominationIntoJSONResponse(Nomination nomination){
        Map<String,Object> nominationResponse = new HashMap<>();
        nominationResponse.put("id",nomination.getNominationId());
        nominationResponse.put("nominee",nomination.getNominee());
        nominationResponse.put("votes",nomination.getVotes().stream().map(VoteService::convertVoteIntoJSONResponse));
        return nominationResponse;
    }

    public static Nomination getNominationByID(Long nominationID, NominationRepository nominationRepository) throws ResponseStatusException {
        Optional<Nomination> nomination = nominationRepository.findById(nominationID);
        if (nomination.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Nomination found with ID: " + nominationID);
        }
        return nomination.get();
    }



    public static Nomination createNominationFromDTO(NominationDTO nominationDTO, User nominator, Poll poll) {
        Nomination nomination = new Nomination();
        nomination.setNominee(nominationDTO.nominee());
        nomination.setNominator(nominator);
        nomination.setPoll(poll);
        return nomination;
    }
}
