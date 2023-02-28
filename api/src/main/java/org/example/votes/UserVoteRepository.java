package org.example.votes;

import org.example.votes.models.UserVote;
import org.springframework.data.repository.CrudRepository;


public interface UserVoteRepository extends CrudRepository<UserVote,Long> {
}
