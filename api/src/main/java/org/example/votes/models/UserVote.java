package org.example.votes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.nominations.models.Nomination;
import org.example.users.models.User;

@Entity
@Table(name = "UserVotes")
public class UserVote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userVote;
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "userId")
  private User user;
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "nominationId")
  private Nomination nomination;


  public long getUserVote() {
    return userVote;
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
