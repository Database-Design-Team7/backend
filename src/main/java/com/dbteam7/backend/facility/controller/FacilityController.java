package com.dbteam7.backend.facility.controller;

import com.dbteam7.backend.facility.dto.FacilityRequestDto;
import com.dbteam7.backend.facility.dto.FacilityResponseDto;
import com.dbteam7.backend.facility.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Facility Management", description = "시설 관리 API")
public class FacilityController {

    private final FacilityService facilityService;

    // 1. 시설 등록
    @PostMapping("/branches/{branchId}/facilities")
    @Operation(summary = "시설 등록", description = "특정 지점에 시설을 등록합니다.")
    public ResponseEntity<FacilityResponseDto> createFacility(
            @PathVariable String branchId,
            @RequestBody FacilityRequestDto requestDto) {
        return ResponseEntity.ok(facilityService.createFacility(branchId, requestDto));
    }

    // 2. 시설 정보 수정
    @PatchMapping("/facilities/{facilityId}")
    @Operation(summary = "시설 정보 수정", description = "시설 정보를 수정합니다.")
    public ResponseEntity<FacilityResponseDto> updateFacility(
            @PathVariable String facilityId,
            @RequestBody FacilityRequestDto requestDto) {
        return ResponseEntity.ok(facilityService.updateFacility(facilityId, requestDto));
    }

    // 3. 지점별 시설 목록 조회
    @GetMapping("/branches/{branchId}/facilities")
    @Operation(summary = "지점별 시설 조회", description = "특정 지점의 모든 시설 목록을 조회합니다.")
    public ResponseEntity<List<FacilityResponseDto>> getFacilitiesByBranch(
            @PathVariable String branchId) {
        return ResponseEntity.ok(facilityService.getFacilitiesByBranchId(branchId));
    }
}