package org.example.branches;

import org.example.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Optional<Branch> findBranchByBranchNameEqualsIgnoreCase(String name);
}
