package com.dbteam7.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRole {
    @Id
    @Column(name = "group_role_id", length = 45)
    private String groupRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Groups groups;

    @Column(name = "role_name", length = 45)
    private String roleName;

    @OneToMany(mappedBy = "groupRole", cascade = CascadeType.ALL)
    private List<RolePermission> rolePermissions = new ArrayList<>();
}

