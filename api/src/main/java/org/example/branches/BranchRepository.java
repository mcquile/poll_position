package org.example.branches;

import org.example.branches.models.Branch;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BranchRepository extends CrudRepository<Branch, Long> {

    Optional<Branch> getBranchByBranchName(String branchName);
}
