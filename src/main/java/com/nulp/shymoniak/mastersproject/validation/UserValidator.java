package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.validation.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator<UserDTO> {
    private final UserRepository repository;
    private final ValidationUtility validationUtility;

    // TODO: 2/11/22 add password validation
    @Override
    public boolean isValid(UserDTO userDTO) {
        return isUsernameValid(userDTO.getUsername())
                && validationUtility.isNotNullAndNotBlank(userDTO.getPassword());
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
}
