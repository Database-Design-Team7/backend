package com.dbteam7.backend.facility.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FacilityRequestDto {
    private String facilityName;
    private Integer capacity;
}