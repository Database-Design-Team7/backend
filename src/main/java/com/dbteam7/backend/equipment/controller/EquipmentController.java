package com.dbteam7.backend.equipment.controller;

import com.dbteam7.backend.equipment.dto.EquipmentRequestDto;
import com.dbteam7.backend.equipment.dto.EquipmentResponseDto;
import com.dbteam7.backend.equipment.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Equipment Management", description = "장비 관리 API")
public class EquipmentController {

    private final EquipmentService equipmentService;

    // 1. 장비 등록
    @PostMapping("/equipment")
    @Operation(summary = "장비 등록", description = "특정 시설에 장비를 등록합니다.")
    public ResponseEntity<EquipmentResponseDto> registerEquipment(@RequestBody EquipmentRequestDto requestDto) {
        return ResponseEntity.ok(equipmentService.registerEquipment(requestDto));
    }

    // 2. 장비 상태 수정
    @PatchMapping("/equipment/{equipmentId}/status")
    @Operation(summary = "장비 상태 수정", description = "장비의 상태(정상, 고장 등)를 변경합니다.")
    public ResponseEntity<EquipmentResponseDto> updateStatus(
            @PathVariable String equipmentId,
            @RequestBody EquipmentRequestDto requestDto) {
        return ResponseEntity.ok(equipmentService.updateEquipmentStatus(equipmentId, requestDto));
    }

    // 3. 시설별 장비 목록 조회
    @GetMapping("/facilities/{facilityId}/equipment")
    @Operation(summary = "시설별 장비 조회", description = "특정 시설에 비치된 장비 목록을 조회합니다.")
    public ResponseEntity<List<EquipmentResponseDto>> getEquipmentsByFacility(
            @PathVariable String facilityId) {
        return ResponseEntity.ok(equipmentService.getEquipmentsByFacility(facilityId));
    }
}