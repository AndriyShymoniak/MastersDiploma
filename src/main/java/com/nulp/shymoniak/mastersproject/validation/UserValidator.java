package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator<UserDTO> {
    private final UserRepository repository;
    private final ValidationUtility validationUtility;

    @Override
    public boolean isValid(UserDTO userDTO) {
        return isUsernameValid(userDTO.getUsername())
                && isPasswordValid(userDTO.getPassword());
    }

    /**
     * Checks whether username is not blank and unique in DB
     * @param username
     * @return true if username is unique in DB and not blank
     */
    private boolean isUsernameValid(String username) {
        return validationUtility.isNotNullAndNotBlank(username)
                && !repository.existsByUsername(username);
    }

    private boolean isPasswordValid(String password) {
        return validationUtility.isNotNullAndNotBlank(password);
    }
}
