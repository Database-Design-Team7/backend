package com.dbteam7.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    @Id
    @Column(name = "facility_id", length = 45)
    private String facilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "facility_name", length = 45)
    private String facilityName;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Equipment> equipments = new ArrayList<>();

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    @Builder
    public Facility(String facilityId, Branch branch, String facilityName, Integer capacity) {
        this.facilityId = facilityId;
        this.branch = branch;
        this.facilityName = facilityName;
        this.capacity = capacity;
    }

    // 정보 수정 메서드 (PATCH)
    public void update(String facilityName, Integer capacity) {
        if (facilityName != null) this.facilityName = facilityName;
        if (capacity != null) this.capacity = capacity;
    }

}

