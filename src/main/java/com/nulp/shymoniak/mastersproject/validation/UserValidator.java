package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<UserDTO> {
    private final UserRepository repository;
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public UserValidator(UserRepository repository, GeneralValidationUtility generalValidationUtility) {
        this.repository = repository;
        this.generalValidationUtility = generalValidationUtility;
    }

    @Override
    public boolean isValid(UserDTO userDTO) {
        return !isUsernameUnique(userDTO.getUsername())
                || !isPasswordValid(userDTO.getPassword())
                || !isUserRoleValid(userDTO.getUserRole())
                || !isPersonValid(userDTO.getPerson());
    }

    private boolean isUsernameUnique(String username) {
        return repository.existsByUsername(username);
    }

    private boolean isPasswordValid(String password) {
        return generalValidationUtility.isNotNullAndNotBlank(password);
    }

    private boolean isUserRoleValid(UserRoleDTO userRoleDTO) {
        return userRoleDTO.getRole() != null;
    }

    private boolean isPersonValid(PersonDTO personDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(personDTO.getName())
                && generalValidationUtility.isNotNullAndNotBlank(personDTO.getSurname())
                && generalValidationUtility.isNotNullAndNotBlank(personDTO.getEmail());
    }
}
