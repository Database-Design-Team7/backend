package com.dbteam7.backend.maintenance.service;

import com.dbteam7.backend.entity.Equipment;
import com.dbteam7.backend.entity.Maintenance;
import com.dbteam7.backend.equipment.repository.EquipmentRepository;
import com.dbteam7.backend.maintenance.dto.MaintenanceRequestDto;
import com.dbteam7.backend.maintenance.dto.MaintenanceResponseDto;
import com.dbteam7.backend.maintenance.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository;

    // 1. 유지보수 내역 등록
    @Transactional
    public MaintenanceResponseDto createMaintenance(MaintenanceRequestDto requestDto) {
        Equipment equipment = equipmentRepository.findById(requestDto.getEquipmentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 장비가 존재하지 않습니다. id=" + requestDto.getEquipmentId()));

        String generatedId = UUID.randomUUID().toString();

        Maintenance maintenance = Maintenance.builder()
                .maintId(generatedId)
                .equipment(equipment)
                .type(requestDto.getType())
                .description(requestDto.getDescription())
                .cost(requestDto.getCost())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .status(requestDto.getStatus())
                .build();

        maintenanceRepository.save(maintenance);
        return new MaintenanceResponseDto(maintenance);
    }

    // 2. 장비별 유지보수 이력 조회
    public List<MaintenanceResponseDto> getMaintenanceListByEquipment(String equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장비가 존재하지 않습니다. id=" + equipmentId));

        return maintenanceRepository.findAllByEquipmentOrderByStartDateDesc(equipment).stream()
                .map(MaintenanceResponseDto::new)
                .collect(Collectors.toList());
    }

    // 3. 유지보수 정보 수정 (필요시 사용)
    @Transactional
    public MaintenanceResponseDto updateMaintenance(String maintId, MaintenanceRequestDto requestDto) {
        Maintenance maintenance = maintenanceRepository.findById(maintId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유지보수 내역이 없습니다. id=" + maintId));

        maintenance.update(
                requestDto.getType(),
                requestDto.getDescription(),
                requestDto.getCost(),
                requestDto.getStartDate(),
                requestDto.getEndDate(),
                requestDto.getStatus()
        );

        return new MaintenanceResponseDto(maintenance);
    }
}