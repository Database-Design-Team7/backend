package com.dbteam7.backend.equipment.repository;

import com.dbteam7.backend.entity.Equipment;
import com.dbteam7.backend.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    // 특정 시설에 속한 장비 목록 조회
    List<Equipment> findAllByFacility(Facility facility);
}