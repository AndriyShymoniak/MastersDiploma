package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator implements Validator<UserRoleDTO> {
    private final ValidationUtility validationUtility;

    @Autowired
    public UserRoleValidator(ValidationUtility validationUtility) {
        this.validationUtility = validationUtility;
    }

    // TODO: 2/9/22 add validation on role written using capital letters (ex. PRIVILEGED_USER)
    @Override
    public boolean isValid(UserRoleDTO userRoleDTO) {
        return validationUtility.isNotNullAndNotBlank(userRoleDTO.getRole());
    }
}
