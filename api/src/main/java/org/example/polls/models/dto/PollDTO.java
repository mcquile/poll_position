package org.example.polls.models.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record PollDTO (UUID pollCreatorID, String title, String description, Timestamp voteStart, Timestamp voteEnd, Timestamp nominationEnd){

}
