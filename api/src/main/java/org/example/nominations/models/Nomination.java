package org.example.nominations.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.example.polls.models.Poll;
import org.example.users.models.User;
import org.example.votes.models.UserVote;

import java.util.List;

@Entity
@Table(name = "Nominations")
public class Nomination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nominationId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "pollId")
    private Poll poll;
    private String nominee;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "nominator")
    private User nominator;

    @OneToMany(mappedBy = "nomination", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserVote> votes;


    public long getNominationId() {
        return nominationId;
    }

    public void setNominationId(long nominationId) {
        this.nominationId = nominationId;
    }


    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }


    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }


    public User getNominator() {
        return nominator;
    }

    public void setNominator(User nominator) {
        this.nominator = nominator;
    }

    public List<UserVote> getVotes() {
        return votes;
    }

    public void setVotes(List<UserVote> votes) {
        this.votes = votes;
    }
}
