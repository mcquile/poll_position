package org.example.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.example.users.models.User;

@Entity
public class SpecificUserRestriction {

    @Id
    private long specificUserRestrictionID;
    @ManyToOne
    private Poll poll;
    @ManyToOne
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
