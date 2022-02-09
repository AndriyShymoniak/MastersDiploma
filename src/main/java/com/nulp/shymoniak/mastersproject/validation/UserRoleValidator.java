package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator implements Validator<UserRoleDTO> {
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public UserRoleValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    // TODO: 2/9/22 add validation on role written using capital letters (ex. PRIVILEGED_USER)
    @Override
    public boolean isValid(UserRoleDTO userRoleDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(userRoleDTO.getRole());
    }
}
