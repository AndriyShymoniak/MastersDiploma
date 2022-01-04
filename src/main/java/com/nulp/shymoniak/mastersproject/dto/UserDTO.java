package com.nulp.shymoniak.mastersproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private Long userRoleId;
    private Long personId;
    private LocalDateTime registeredDate;
}
