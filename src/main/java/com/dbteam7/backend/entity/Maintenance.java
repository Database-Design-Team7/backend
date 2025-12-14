package com.dbteam7.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@NoArgsConstructor
public class Maintenance {
    @Id
    @Column(name = "maint_id", length = 30)
    private String maintId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private Boolean status;

    @Builder
    public Maintenance(String maintId, Equipment equipment, String type, String description, Integer cost, LocalDateTime startDate, LocalDateTime endDate, Boolean status) {
        this.maintId = maintId;
        this.equipment = equipment;
        this.type = type;
        this.description = description;
        this.cost = cost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // 정보 수정 메서드
    public void update(String type, String description, Integer cost, LocalDateTime startDate, LocalDateTime endDate, Boolean status) {
        if (type != null) this.type = type;
        if (description != null) this.description = description;
        if (cost != null) this.cost = cost;
        if (startDate != null) this.startDate = startDate;
        if (endDate != null) this.endDate = endDate;
        if (status != null) this.status = status;
    }

}

