package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import com.nulp.shymoniak.mastersproject.utility.impl.ValidationUtilityImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationUserValidatorTest {
    private final UserValidator userValidator;
    private final ValidationUtilityImpl validationUtilityImpl;

    @Mock
    private ApplicationUserRepository userRepository;

    @Autowired
    public ApplicationUserValidatorTest(UserValidator userValidator, ValidationUtilityImpl validationUtilityImpl) {
        this.userValidator = userValidator;
        this.validationUtilityImpl = validationUtilityImpl;
    }

    @Test
    public void validateUsernameOnNotBeingBlank() {
        // Given
        ApplicationUserDTO invalidUser1 = new ApplicationUserDTO(999L, null, "password", LocalDateTime.now(), null, null);
        ApplicationUserDTO invalidUser2 = new ApplicationUserDTO(999L, "", "password", LocalDateTime.now(), null, null);
        ApplicationUserDTO validUser1 = new ApplicationUserDTO(999L, "username", "password", LocalDateTime.now(), null, null);
        // Then
        assertFalse(userValidator.isValid(invalidUser1));
        assertFalse(userValidator.isValid(invalidUser2));
        assertTrue(userValidator.isValid(validUser1));
    }

    @Test
    public void validateUsernameOnBeingUniqueInDB(){
        //given
        ApplicationUserDTO existingUser = new ApplicationUserDTO(999L, "existingUser", "password", LocalDateTime.now(), null, null);
        ApplicationUserDTO newUser = new ApplicationUserDTO(999L, "newUser", "password", LocalDateTime.now(), null, null);
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