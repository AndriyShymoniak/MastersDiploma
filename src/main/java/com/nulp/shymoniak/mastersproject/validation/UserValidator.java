package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<UserDTO> {
    private final UserRepository repository;
    private final GeneralValidationUtility generalValidationUtility;
    private final UserRoleValidator userRoleValidator;
    private final PersonValidator personValidator;

    @Autowired
    public UserValidator(UserRepository repository, GeneralValidationUtility generalValidationUtility, UserRoleValidator userRoleValidator, PersonValidator personValidator) {
        this.repository = repository;
        this.generalValidationUtility = generalValidationUtility;
        this.userRoleValidator = userRoleValidator;
        this.personValidator = personValidator;
    }

    @Override
    public boolean isValid(UserDTO userDTO) {
        return isUsernameValid(userDTO.getUsername())
                && isPasswordValid(userDTO.getPassword())
                && userRoleValidator.isValid(userDTO.getUserRole())
                && personValidator.isValid(userDTO.getPerson());
    }

    private boolean isUsernameValid(String username) {
        return generalValidationUtility.isNotNullAndNotBlank(username)
                && !repository.existsByUsername(username);  // username should be unique in DB
    }

    private boolean isPasswordValid(String password) {
        return generalValidationUtility.isNotNullAndNotBlank(password);
    }
}
