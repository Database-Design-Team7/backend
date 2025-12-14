package com.dbteam7.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    @Column(name = "equipment_id", length = 45)
    private String equipmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Column(name = "type", length = 45)
    private String type;

    @Column(name = "status", length = 45)
    private String status;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<Maintenance> maintenances = new ArrayList<>();

    @Builder
    public Equipment(String equipmentId, Facility facility, String type, String status) {
        this.equipmentId = equipmentId;
        this.facility = facility;
        this.type = type;
        this.status = status;
    }

    // 상태 변경 메서드 (PATCH)
    public void updateStatus(String status) {
        if (status != null) {
            this.status = status;
        }
    }
}

