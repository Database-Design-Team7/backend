package com.dbteam7.backend.equipment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EquipmentRequestDto {
    private String facilityId; // 등록 시 필요 (어느 시설에?)
    private String type;       // 장비 종류/이름
    private String status;     // 상태 (정상, 고장 등)
}