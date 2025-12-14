package com.dbteam7.backend.facility.service;

import com.dbteam7.backend.branch.repository.BranchRepository;
import com.dbteam7.backend.entity.Branch;
import com.dbteam7.backend.entity.Facility;
import com.dbteam7.backend.facility.dto.FacilityRequestDto;
import com.dbteam7.backend.facility.dto.FacilityResponseDto;
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
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final BranchRepository branchRepository; // 지점 정보 조회를 위해 필요

    // 1. 시설 등록 (POST /api/v1/branches/{branchId}/facilities)
    @Transactional
    public FacilityResponseDto createFacility(String branchId, FacilityRequestDto requestDto) {
        // 소속 지점 확인
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 존재하지 않습니다. id=" + branchId));

        String generatedId = UUID.randomUUID().toString();

        Facility facility = Facility.builder()
                .facilityId(generatedId)
                .branch(branch) // 조회한 지점 객체 설정 (관계 매핑)
                .facilityName(requestDto.getFacilityName())
                .capacity(requestDto.getCapacity())
                .build();

        facilityRepository.save(facility);
        return new FacilityResponseDto(facility);
    }

    // 2. 시설 정보 수정 (PATCH /api/v1/facilities/{facilityId})
    @Transactional
    public FacilityResponseDto updateFacility(String facilityId, FacilityRequestDto requestDto) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 시설이 존재하지 않습니다. id=" + facilityId));

        facility.update(requestDto.getFacilityName(), requestDto.getCapacity());

        return new FacilityResponseDto(facility);
    }

    // 3. 지점별 시설 목록 조회 (GET /api/v1/branches/{branchId}/facilities)
    public List<FacilityResponseDto> getFacilitiesByBranchId(String branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 존재하지 않습니다. id=" + branchId));

        return facilityRepository.findAllByBranch(branch).stream()
                .map(FacilityResponseDto::new)
                .collect(Collectors.toList());
    }
}