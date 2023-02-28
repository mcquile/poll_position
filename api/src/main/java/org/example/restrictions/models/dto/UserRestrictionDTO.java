package org.example.restrictions.models.dto;

import java.util.Date;
import java.util.Optional;

public record UserRestrictionDTO(Optional<String> firstNamePattern,
                                 Optional<String> lastNamePattern,
                                 Optional<String> branchName,
                                 Optional<Boolean> male,
                                 Optional<Date> olderThan,
                                 Optional<Date> youngerThan) {
}
