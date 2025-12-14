package com.dbteam7.backend.equipment.service;

import com.dbteam7.backend.entity.Equipment;
import com.dbteam7.backend.entity.Facility;
import com.dbteam7.backend.equipment.dto.EquipmentRequestDto;
import com.dbteam7.backend.equipment.dto.EquipmentResponseDto;
import com.dbteam7.backend.equipment.repository.EquipmentRepository;
import com.dbteam7.backend.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final FacilityRepository facilityRepository;

    // 1. 장비 등록 (POST /api/v1/equipment)
    @Transactional
    public EquipmentResponseDto registerEquipment(EquipmentRequestDto requestDto) {
        // 시설 조회
        Facility facility = facilityRepository.findById(requestDto.getFacilityId())
                .orElseThrow(() -> new IllegalArgumentException("해당 시설이 존재하지 않습니다. id=" + requestDto.getFacilityId()));

        String generatedId = UUID.randomUUID().toString();

        Equipment equipment = Equipment.builder()
                .equipmentId(generatedId)
                .facility(facility)
                .type(requestDto.getType())
                .status(requestDto.getStatus()) // 초기 상태 (예: "NORMAL")
                .build();

        equipmentRepository.save(equipment);
        return new EquipmentResponseDto(equipment);
    }

    // 2. 장비 상태 수정 (PATCH /api/v1/equipment/{equipmentId}/status)
    @Transactional
    public EquipmentResponseDto updateEquipmentStatus(String equipmentId, EquipmentRequestDto requestDto) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장비가 존재하지 않습니다. id=" + equipmentId));

        // 상태 변경
        equipment.updateStatus(requestDto.getStatus());

        // TODO: 유지보수(Maintenance) 내역 기록 로직 추가 필요
        // Maintenance maintenance = Maintenance.builder()...build();
        // maintenanceRepository.save(maintenance);

        return new EquipmentResponseDto(equipment);
    }

    // 3. 시설별 장비 목록 조회 (GET /api/v1/facilities/{facilityId}/equipment)
    public List<EquipmentResponseDto> getEquipmentsByFacility(String facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 시설이 존재하지 않습니다. id=" + facilityId));

        return equipmentRepository.findAllByFacility(facility).stream()
                .map(EquipmentResponseDto::new)
                .collect(Collectors.toList());
    }
}