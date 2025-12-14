package com.dbteam7.backend.maintenance.repository;

import com.dbteam7.backend.entity.Equipment;
import com.dbteam7.backend.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, String> {
    // 특정 장비의 유지보수 이력 전체 조회 (최신순 정렬 권장)
    List<Maintenance> findAllByEquipmentOrderByStartDateDesc(Equipment equipment);
}