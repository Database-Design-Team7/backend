package com.dbteam7.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "branch")
public class Branch {

    @Id
    @Column(name = "branch_id", length = 45)
    private String branchId; // String ID

    @Column(nullable = false, unique = true, length = 45)
    private String name; // 지점명

    @Column(name = "address", length = 45, nullable = false)
    private String address; // 주소

    @Column(name = "contact_number", length = 20)
    private String contactNumber; // 연락처

    @Column(name = "operating_hours", length = 100)
    private String operatingHours; // 운영시간

    @Column(name = "charge_rate", length = 45)
    private String chargeRate; // 요금 정책

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Facility> facilities = new ArrayList<>();

    @Builder
    public Branch(String branchId, String name, String address, String contactNumber, String operatingHours, String chargeRate) {
        this.branchId = branchId;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.operatingHours = operatingHours;
        this.chargeRate = chargeRate;
    }

    // PATCH 요청 처리를 위한 수정 메서드
    public void update(String name, String address, String contactNumber, String operatingHours, String chargeRate) {
        if (name != null) this.name = name;
        if (address != null) this.address = address;
        if (contactNumber != null) this.contactNumber = contactNumber;
        if (operatingHours != null) this.operatingHours = operatingHours;
        if (chargeRate != null) this.chargeRate = chargeRate;
    }
}