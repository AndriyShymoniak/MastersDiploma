package com.nulp.shymoniak.mastersproject.utility.validator;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<UserDTO> {
    private final UserRepository repository;
    private final GeneralValidator generalValidator;

    @Autowired
    public UserValidator(UserRepository repository, GeneralValidator generalValidator) {
        this.repository = repository;
        this.generalValidator = generalValidator;
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
        return generalValidator.isNotNullAndNotBlank(password);
    }

    private boolean isUserRoleValid(UserRoleDTO userRoleDTO) {
        return userRoleDTO.getRole() != null;
    }

    private boolean isPersonValid(PersonDTO personDTO) {
        return generalValidator.isNotNullAndNotBlank(personDTO.getName())
                && generalValidator.isNotNullAndNotBlank(personDTO.getSurname())
                && generalValidator.isNotNullAndNotBlank(personDTO.getEmail());
    }
}
