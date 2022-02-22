package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator<ApplicationUserDTO> {
    private final UserRepository repository;
    private final ValidationUtility validationUtility;

    @Override
    public boolean isValid(ApplicationUserDTO userDTO) {
        return isUsernameValid(userDTO.getUsername())
                && validationUtility.isValidUsernameOrPassword(userDTO.getPassword());
    }

    /**
     * Checks whether username is not blank and unique in DB.
     * Also checks if username is not shorter than 4 symbols,
     * and contains only letters, numbers and -_. symbols
     * @param username
     * @return true if username matches username requirements
     */
    private boolean isUsernameValid(String username) {
        return validationUtility.isValidUsernameOrPassword(username)
                && !repository.existsByUsername(username);
    }
}
