package com.dbteam7.backend.maintenance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MaintenanceRequestDto {
    private String equipmentId; // 어떤 장비에 대한 유지보수인지
    private String type;
    private String description;
    private Integer cost;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean status;
}