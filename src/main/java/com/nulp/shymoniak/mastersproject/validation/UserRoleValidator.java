package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator implements Validator<UserRoleDTO> {
    @Override
    public boolean isValid(UserRoleDTO userRoleDTO) {
        return userRoleDTO.getRole() != null;
    }
}
