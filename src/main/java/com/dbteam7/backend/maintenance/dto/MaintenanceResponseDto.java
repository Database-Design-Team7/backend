package com.dbteam7.backend.maintenance.dto;

import com.dbteam7.backend.entity.Maintenance;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MaintenanceResponseDto {
    private String maintId;
    private String equipmentId;
    private String type;
    private String description;
    private Integer cost;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean status;

    public MaintenanceResponseDto(Maintenance maintenance) {
        this.maintId = maintenance.getMaintId();
        this.equipmentId = maintenance.getEquipment().getEquipmentId();
        this.type = maintenance.getType();
        this.description = maintenance.getDescription();
        this.cost = maintenance.getCost();
        this.startDate = maintenance.getStartDate();
        this.endDate = maintenance.getEndDate();
        this.status = maintenance.getStatus();
    }
}