package com.dbteam7.backend.branch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BranchRequestDto {
    private String name;
    private String address;
    private String contactNumber;
    private String operatingHours;
    private String chargeRate;
}