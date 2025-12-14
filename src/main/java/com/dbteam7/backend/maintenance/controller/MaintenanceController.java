package com.dbteam7.backend.maintenance.controller;

import com.dbteam7.backend.maintenance.dto.MaintenanceRequestDto;
import com.dbteam7.backend.maintenance.dto.MaintenanceResponseDto;
import com.dbteam7.backend.maintenance.service.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Maintenance Management", description = "유지보수 관리 API")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping("/maintenance")
    @Operation(summary = "유지보수 내역 등록", description = "장비의 수리/점검 내역을 등록합니다.")
    public ResponseEntity<MaintenanceResponseDto> createMaintenance(@RequestBody MaintenanceRequestDto requestDto) {
        return ResponseEntity.ok(maintenanceService.createMaintenance(requestDto));
    }

    @GetMapping("/equipments/{equipmentId}/maintenance")
    @Operation(summary = "장비별 유지보수 이력 조회", description = "특정 장비의 모든 유지보수 기록을 조회합니다.")
    public ResponseEntity<List<MaintenanceResponseDto>> getMaintenanceHistory(@PathVariable String equipmentId) {
        return ResponseEntity.ok(maintenanceService.getMaintenanceListByEquipment(equipmentId));
    }

    @PatchMapping("/maintenance/{maintId}")
    @Operation(summary = "유지보수 내역 수정", description = "유지보수 기록을 수정합니다.")
    public ResponseEntity<MaintenanceResponseDto> updateMaintenance(
            @PathVariable String maintId,
            @RequestBody MaintenanceRequestDto requestDto) {
        return ResponseEntity.ok(maintenanceService.updateMaintenance(maintId, requestDto));
    }
}