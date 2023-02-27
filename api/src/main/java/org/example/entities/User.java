package org.example.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "Users")
public class User {

  @Id
  private UUID userId;
  private String firstName;
  private String lastName;
  @ManyToOne
  private Branch branch;
  private java.sql.Date dateOfBirth;
  private String profilePicLink;
  private String email;
  @ManyToOne
  private Sex sex;


  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public Branch getBranch() {
    return branch;
  }

  public void setBranch(Branch branchId) {
    this.branch = branchId;
  }


  public java.sql.Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(java.sql.Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }


  public String getProfilePicLink() {
    return profilePicLink;
  }

  public void setProfilePicLink(String profilePicLink) {
    this.profilePicLink = profilePicLink;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public Sex getSex() {
    return sex;
  }

  public void setSex(Sex sexId) {
    this.sex = sexId;
  }

}
