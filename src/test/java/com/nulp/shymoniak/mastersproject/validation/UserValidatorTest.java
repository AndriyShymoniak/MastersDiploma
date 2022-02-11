package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.validation.ValidationUtilityImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserValidatorTest {
    private final UserValidator userValidator;
    private final ValidationUtilityImpl validationUtilityImpl;

    @Mock
    private UserRepository userRepository;

    @Autowired
    public UserValidatorTest(UserValidator userValidator, ValidationUtilityImpl validationUtilityImpl) {
        this.userValidator = userValidator;
        this.validationUtilityImpl = validationUtilityImpl;
    }

    @Test
    public void validateUsernameOnNotBeingBlank() {
        // given
        UserDTO invalidUser1 = new UserDTO(999L, null, "password", LocalDateTime.now(), null, null);
        UserDTO invalidUser2 = new UserDTO(999L, "", "password", LocalDateTime.now(), null, null);
        UserDTO validUser1 = new UserDTO(999L, "username", "password", LocalDateTime.now(), null, null);
        // then
        assertFalse(userValidator.isValid(invalidUser1));
        assertFalse(userValidator.isValid(invalidUser2));
        assertTrue(userValidator.isValid(validUser1));
    }

    @Test
    public void validateUsernameOnBeingUniqueInDB(){
        //given
        UserDTO existingUser = new UserDTO(999L, "existingUser", "password", LocalDateTime.now(), null, null);
        UserDTO newUser = new UserDTO(999L, "newUser", "password", LocalDateTime.now(), null, null);
        //when
        Mockito.when(userRepository.existsByUsername(existingUser.getUsername())).thenReturn(true);
        Mockito.when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        //then
        assertFalse(isUsernameValid(existingUser.getUsername()));
        assertTrue(isUsernameValid(newUser.getUsername()));
    }

    private boolean isUsernameValid(String username) {
        return validationUtilityImpl.isNotNullAndNotBlank(username)
                && !userRepository.existsByUsername(username);
    }
}