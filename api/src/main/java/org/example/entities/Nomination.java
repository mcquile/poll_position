package org.example.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Nominations")
public class Nomination {

  @Id
  private long nominationId;
  @ManyToOne
  private Poll poll;
  private String nominee;
  @ManyToOne
  private User nominator;


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

}
