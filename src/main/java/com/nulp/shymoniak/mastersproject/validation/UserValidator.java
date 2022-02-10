package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO: 2/10/22 remove related objects validation
@Component
public class UserValidator implements Validator<UserDTO> {
    private final UserRepository repository;
    private final ValidationUtility validationUtility;
    private final UserRoleValidator userRoleValidator;
    private final PersonValidator personValidator;

    @Autowired
    public UserValidator(UserRepository repository, ValidationUtility validationUtility, UserRoleValidator userRoleValidator, PersonValidator personValidator) {
        this.repository = repository;
        this.validationUtility = validationUtility;
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
        return validationUtility.isNotNullAndNotBlank(username)
                && !repository.existsByUsername(username);  // username should be unique in DB
    }

    private boolean isPasswordValid(String password) {
        return validationUtility.isNotNullAndNotBlank(password);
    }
}
