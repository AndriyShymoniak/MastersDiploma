package com.nulp.shymoniak.mastersproject.dto;

import com.nulp.shymoniak.mastersproject.entity.enums.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserDTO {
    private Long userId;
    private String username;
    private String password;
    private LocalDateTime createDate;

    private ApplicationUserRole role;
    private PersonDTO person;
}
