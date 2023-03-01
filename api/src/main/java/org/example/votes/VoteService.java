package org.example.votes;

import org.example.votes.models.UserVote;

import java.util.HashMap;
import java.util.Map;

public class VoteService {

    private  VoteService(){

    }


    public static Map<String,Object> convertVoteIntoJSONResponse(UserVote vote){
        Map<String,Object> voteResponse = new HashMap<>();
        voteResponse.put("id",vote.getUserVote());
        return voteResponse;
    }

}
