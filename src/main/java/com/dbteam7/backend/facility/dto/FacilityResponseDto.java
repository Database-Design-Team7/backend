package com.dbteam7.backend.facility.dto;

import com.dbteam7.backend.entity.Facility;
import lombok.Getter;

@Getter
public class FacilityResponseDto {
    private String facilityId;
    private String branchId; // 소속 지점 ID
    private String facilityName;
    private Integer capacity;

    public FacilityResponseDto(Facility facility) {
        this.facilityId = facility.getFacilityId();
        this.branchId = facility.getBranch().getBranchId(); // 지점 ID 추출
        this.facilityName = facility.getFacilityName();
        this.capacity = facility.getCapacity();
    }
}