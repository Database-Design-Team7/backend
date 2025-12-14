package com.dbteam7.backend.facility.repository;

import com.dbteam7.backend.entity.Branch;
import com.dbteam7.backend.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, String> {
    // 특정 지점(Branch)에 속한 모든 시설 조회
    List<Facility> findAllByBranch(Branch branch);
}