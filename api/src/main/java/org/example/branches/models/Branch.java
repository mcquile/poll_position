package org.example.branches.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Branches")
public class Branch {
    @Id
    private long branchId;
    private String branchName;


    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

}
