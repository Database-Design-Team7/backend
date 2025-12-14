package com.dbteam7.backend.branch.service;

import com.dbteam7.backend.branch.dto.BranchRequestDto;
import com.dbteam7.backend.branch.dto.BranchResponseDto;
import com.dbteam7.backend.branch.repository.BranchRepository;
import com.dbteam7.backend.entity.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchService {

    private final BranchRepository branchRepository;

    // 1. 지점 등록
    @Transactional
    public BranchResponseDto createBranch(BranchRequestDto requestDto) {
        if (branchRepository.existsByName(requestDto.getName())) {
            throw new IllegalArgumentException("이미 존재하는 지점명입니다.");
        }

        // String ID 직접 생성 (UUID)
        String generatedId = UUID.randomUUID().toString();

        Branch branch = Branch.builder()
                .branchId(generatedId)
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .contactNumber(requestDto.getContactNumber())
                .operatingHours(requestDto.getOperatingHours())
                .chargeRate(requestDto.getChargeRate())
                .build();

        branchRepository.save(branch);
        return new BranchResponseDto(branch);
    }

    // 2. 지점 정보 수정 (PATCH)
    @Transactional
    public BranchResponseDto updateBranch(String branchId, BranchRequestDto requestDto) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 존재하지 않습니다. id=" + branchId));

        if (requestDto.getName() != null && !branch.getName().equals(requestDto.getName())) {
            if (branchRepository.existsByName(requestDto.getName())) {
                throw new IllegalArgumentException("이미 존재하는 지점명입니다.");
            }
        }

        branch.update(
                requestDto.getName(),
                requestDto.getAddress(),
                requestDto.getContactNumber(),
                requestDto.getOperatingHours(),
                requestDto.getChargeRate()
        );

        return new BranchResponseDto(branch);
    }

    // 3. 지점 삭제
    @Transactional
    public void deleteBranch(String branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 존재하지 않습니다. id=" + branchId));

        branchRepository.delete(branch);
    }
}