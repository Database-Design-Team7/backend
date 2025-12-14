package com.dbteam7.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "user_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserGroup.UserGroupId.class)
public class UserGroup {
    @Id
    @Column(name = "user_id", length = 20)
    private String userId;

    @Id
    @Column(name = "group_id", length = 45)
    private String groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Groups groups;

    @Column(name = "role", length = 45)
    private String role;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UserGroupId implements Serializable {
        private String userId;
        private String groupId;
    }
}

