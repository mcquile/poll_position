package org.example.restrictions.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.branches.models.Branch;
import org.example.polls.models.Poll;
import org.example.sexes.models.Sex;

import java.util.Date;

@Entity
@Table(name = "UserRestrictions")
public class UserRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userRestrictionId;
    private String firstNamePattern;
    private String lastNamePattern;
    @ManyToOne
    @JoinColumn(name = "sexRestrictedTo")
    private Sex sexRestrictedTo;
    @ManyToOne
    @JoinColumn(name = "branchRestriction")
    private Branch branchRestriction;
    private Date dateOfBirthYounger;
    private Date dateOfBirthOlder;
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

    public Date getDateOfBirthYounger() {
        return dateOfBirthYounger;
    }

    public void setDateOfBirthYounger(Date dateOfBirthYounger) {
        this.dateOfBirthYounger = dateOfBirthYounger;
    }

    public Date getDateOfBirthOlder() {
        return dateOfBirthOlder;
    }

    public void setDateOfBirthOlder(Date dateOfBirthOlder) {
        this.dateOfBirthOlder = dateOfBirthOlder;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}