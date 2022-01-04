package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role")
    private Long userRoleId;

    @Column(name = "role")
    private String role;
}
