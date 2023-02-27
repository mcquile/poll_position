package org.example.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.polls.models.Poll;
import org.example.users.models.User;

@Entity
@Table(name = "SpecificUserRestrictions")
public class SpecificUserRestriction {

    @Id
    private long specificUserRestrictionID;
    @ManyToOne
    @JoinColumn(name = "PollId")
    @JsonBackReference
    private Poll poll;
    @ManyToOne
    @JoinColumn(name = "UserId")
    @JsonBackReference
    private User user;
    private boolean restricted;


    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public boolean getRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public long getSpecificUserRestrictionID() {
        return specificUserRestrictionID;
    }

    public void setSpecificUserRestrictionID(long specificUserRestrictionID) {
        this.specificUserRestrictionID = specificUserRestrictionID;
    }
}
