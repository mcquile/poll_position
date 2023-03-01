package org.example.polls.models.dto;

import org.example.nominations.models.dto.NominationDTO;
import org.example.restrictions.models.dto.SpecificUserRestrictionDTO;
import org.example.restrictions.models.dto.UserRestrictionDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public record PollDTO(String title, String description,
                      Optional<List<SpecificUserRestrictionDTO>> specificUserRestrictions,
                      Optional<List<UserRestrictionDTO>> genericRestrictions,
                      Optional<List<NominationDTO>> nominations,
                      Timestamp voteStart, Timestamp voteEnd,
                      Timestamp nominationEnd) {
}