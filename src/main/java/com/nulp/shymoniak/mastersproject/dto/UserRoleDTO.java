package com.nulp.shymoniak.mastersproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private Long userRoleId;
    private String role;
}
