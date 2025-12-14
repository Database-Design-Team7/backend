package com.dbteam7.backend.equipment.dto;

import com.dbteam7.backend.entity.Equipment;
import lombok.Getter;

@Getter
public class EquipmentResponseDto {
    private String equipmentId;
    private String facilityId;
    private String facilityName; // 편의상 시설 이름도 같이 반환
    private String type;
    private String status;

    public EquipmentResponseDto(Equipment equipment) {
        this.equipmentId = equipment.getEquipmentId();
        this.facilityId = equipment.getFacility().getFacilityId();
        this.facilityName = equipment.getFacility().getFacilityName();
        this.type = equipment.getType();
        this.status = equipment.getStatus();
    }
}