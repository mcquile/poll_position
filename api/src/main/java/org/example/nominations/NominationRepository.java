package org.example.nominations;

import org.example.nominations.models.Nomination;
import org.springframework.data.repository.CrudRepository;

public interface NominationRepository extends CrudRepository<Nomination,Long> {
}
