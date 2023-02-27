package org.example.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.polls.models.Poll;

@Entity
@Table(name = "UserRestrictions")
public class UserRestriction {

  @Id
  private long userRestrictionId;
  private String firstNamePattern;
  private String lastNamePattern;
  @ManyToOne
  @JoinColumn(name = "sexRestrictedTo")
  private Sex sexRestrictedTo;
  @ManyToOne
  @JoinColumn(name = "branchRestriction")
  private Branch branchRestriction;
  private java.sql.Date dateOfBirthYounger;
  private java.sql.Date dateOfBirthOlder;
  @ManyToOne
  @JoinColumn(name = "PollId")
  @JsonBackReference
  private Poll poll;


  public long getUserRestrictionId() {
    return userRestrictionId;
  }

  public void setUserRestrictionId(long userRestrictionId) {
    this.userRestrictionId = userRestrictionId;
  }


  public String getFirstNamePattern() {
    return firstNamePattern;
  }

  public void setFirstNamePattern(String firstNamePattern) {
    this.firstNamePattern = firstNamePattern;
  }


  public String getLastNamePattern() {
    return lastNamePattern;
  }

  public void setLastNamePattern(String lastNamePattern) {
    this.lastNamePattern = lastNamePattern;
  }


  public Sex getSexRestrictedTo() {
    return sexRestrictedTo;
  }

  public void setSexRestrictedTo(Sex sexRestrictedTo) {
    this.sexRestrictedTo = sexRestrictedTo;
  }


  public Branch getBranchRestriction() {
    return branchRestriction;
  }

  public void setBranchRestriction(Branch branchRestriction) {
    this.branchRestriction = branchRestriction;
  }


  public java.sql.Date getDateOfBirthYounger() {
    return dateOfBirthYounger;
  }

  public void setDateOfBirthYounger(java.sql.Date dateOfBirthYounger) {
    this.dateOfBirthYounger = dateOfBirthYounger;
  }


  public java.sql.Date getDateOfBirthOlder() {
    return dateOfBirthOlder;
  }

  public void setDateOfBirthOlder(java.sql.Date dateOfBirthOlder) {
    this.dateOfBirthOlder = dateOfBirthOlder;
  }


  public Poll getPoll() {
    return poll;
  }

  public void setPoll(Poll poll) {
    this.poll = poll;
  }

}
