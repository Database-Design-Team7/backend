package com.dbteam7.backend.branch.repository;

import com.dbteam7.backend.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, String> {
    boolean existsByName(String name);
}