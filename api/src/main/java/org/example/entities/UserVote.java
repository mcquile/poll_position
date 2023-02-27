package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.example.users.models.User;

@Entity
@Table(name = "UserVotes")
public class UserVote {

  @Id
  private long userVoteId;
  @ManyToOne
  private User user;
  @ManyToOne
  private Nomination nomination;


  public long getUserVoteId() {
    return userVoteId;
  }

  public void setUserVoteId(long userVoteId) {
    this.userVoteId = userVoteId;
  }


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }


  public Nomination getNomination() {
    return nomination;
  }

  public void setNomination(Nomination nomination) {
    this.nomination = nomination;
  }

}
