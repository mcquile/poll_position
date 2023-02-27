package org.example.polls;

import org.example.polls.models.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PollRepository extends CrudRepository<Poll, UUID> {

    List<Poll> findAllBy();
}
