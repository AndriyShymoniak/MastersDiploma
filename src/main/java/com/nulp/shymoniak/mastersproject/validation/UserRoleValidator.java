package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRoleValidator implements Validator<UserRoleDTO> {
    private final ValidationUtility validationUtility;

    @Override
    public boolean isValid(UserRoleDTO userRoleDTO) {
        return validationUtility.isValidStrCapitalAndUnderscoresOnly(userRoleDTO.getRole());
    }
}
