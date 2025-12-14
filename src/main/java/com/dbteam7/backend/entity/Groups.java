package com.dbteam7.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Groups {
    @Id
    @Column(name = "group_id", length = 45)
    private String groupId;

    @Column(name = "group_name", length = 45)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private User leader;

    @OneToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private List<GroupRole> groupRoles = new ArrayList<>();

    @OneToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();
}

