package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserCredentials")
public class UserCredential {

  @Id
  private long userCredentialId;
  private String passwordHash;
  @OneToOne
  private User user;
  private String salt;


  public long getUserCredentialId() {
    return userCredentialId;
  }

  public void setUserCredentialId(long userCredentialId) {
    this.userCredentialId = userCredentialId;
  }


  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }


  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

}
