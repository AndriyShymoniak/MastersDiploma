package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "user_role_id")
    private Long userRoleId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "registered_date")
    private LocalDateTime registeredDate;
}
