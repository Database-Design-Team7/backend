package com.dbteam7.backend.branch.dto;

import com.dbteam7.backend.entity.Branch;
import lombok.Getter;

@Getter
public class BranchResponseDto {
    private String branchId;
    private String name;
    private String address;
    private String contactNumber;
    private String operatingHours;
    private String chargeRate;

    public BranchResponseDto(Branch branch) {
        this.branchId = branch.getBranchId();
        this.name = branch.getName();
        this.address = branch.getAddress();
        this.contactNumber = branch.getContactNumber();
        this.operatingHours = branch.getOperatingHours();
        this.chargeRate = branch.getChargeRate();
    }
}