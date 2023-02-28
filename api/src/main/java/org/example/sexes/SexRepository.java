package org.example.sexes;

import org.example.sexes.models.Sex;
import org.springframework.data.repository.CrudRepository;

public interface SexRepository extends CrudRepository<Sex, Boolean> {
    Sex getSexBySexId(boolean sexId);
}