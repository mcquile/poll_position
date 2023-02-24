package org.example.repositories;

import org.example.entities.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollsRepository extends CrudRepository<Poll,Long> {
}
