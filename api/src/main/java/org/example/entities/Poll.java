package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "Polls")
public class Poll {

  @Id
  private UUID pollId;
  private String pollCreator;
  private String title;
  private String description;
  private java.sql.Timestamp voteStart;
  private java.sql.Timestamp voteEnd;
  private java.sql.Timestamp nominationEndTime;
  private java.sql.Timestamp pollCreationTime;


  public UUID getPollId() {
    return pollId;
  }

  public void setPollId(UUID pollId) {
    this.pollId = pollId;
  }


  public String getPollCreator() {
    return pollCreator;
  }

  public void setPollCreator(String pollCreator) {
    this.pollCreator = pollCreator;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public java.sql.Timestamp getVoteStart() {
    return voteStart;
  }

  public void setVoteStart(java.sql.Timestamp voteStart) {
    this.voteStart = voteStart;
  }


  public java.sql.Timestamp getVoteEnd() {
    return voteEnd;
  }

  public void setVoteEnd(java.sql.Timestamp voteEnd) {
    this.voteEnd = voteEnd;
  }


  public java.sql.Timestamp getNominationEndTime() {
    return nominationEndTime;
  }

  public void setNominationEndTime(java.sql.Timestamp nominationEndTime) {
    this.nominationEndTime = nominationEndTime;
  }


  public java.sql.Timestamp getPollCreationTime() {
    return pollCreationTime;
  }

  public void setPollCreationTime(java.sql.Timestamp pollCreationTime) {
    this.pollCreationTime = pollCreationTime;
  }

}
