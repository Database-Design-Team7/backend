package com.dbteam7.backend.branch.controller;

import com.dbteam7.backend.branch.dto.BranchRequestDto;
import com.dbteam7.backend.branch.dto.BranchResponseDto;
import com.dbteam7.backend.branch.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Tag(name = "Branch Management", description = "지점 관리 API")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    @Operation(summary = "지점 등록", description = "새로운 지점을 등록합니다.")
    public ResponseEntity<BranchResponseDto> createBranch(@RequestBody BranchRequestDto requestDto) {
        return ResponseEntity.ok(branchService.createBranch(requestDto));
    }

    @PatchMapping("/{branchId}")
    @Operation(summary = "지점 정보 수정", description = "지점 정보를 수정합니다.")
    public ResponseEntity<BranchResponseDto> updateBranch(
            @PathVariable String branchId,
            @RequestBody BranchRequestDto requestDto) {
        return ResponseEntity.ok(branchService.updateBranch(branchId, requestDto));
    }

    @DeleteMapping("/{branchId}")
    @Operation(summary = "지점 삭제", description = "지점을 삭제합니다.")
    public ResponseEntity<String> deleteBranch(@PathVariable String branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.ok("지점이 성공적으로 삭제되었습니다.");
    }
}