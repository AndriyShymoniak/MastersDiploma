package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserValidatorTest {
    private final UserValidator userValidator;
    private final GeneralValidationUtility generalValidationUtility;
    private static PersonDTO validPerson;
    private static UserRoleDTO validUserRole;

    @Mock
    private UserRepository userRepository;

    @Autowired
    public UserValidatorTest(UserValidator userValidator, GeneralValidationUtility generalValidationUtility) {
        this.userValidator = userValidator;
        this.generalValidationUtility = generalValidationUtility;
    }

    @BeforeAll
    public static void setUp() {
        validPerson = new PersonDTO(999L, "Anton", "Surname", "test@email.com");
        validUserRole = new UserRoleDTO(999L, "ROLE");
    }

    @Test
    public void validateUsernameOnNotBeingBlank() {
        // given
        UserDTO invalidUser1 = new UserDTO(999L, null, "password", LocalDateTime.now(), validPerson, validUserRole);
        UserDTO invalidUser2 = new UserDTO(999L, "", "password", LocalDateTime.now(), validPerson, validUserRole);
        UserDTO validUser1 = new UserDTO(999L, "username", "password", LocalDateTime.now(), validPerson, validUserRole);
        // then
        assertFalse(userValidator.isValid(invalidUser1));
        assertFalse(userValidator.isValid(invalidUser2));
        assertTrue(userValidator.isValid(validUser1));
    }

    @Test
    public void validateUsernameOnBeingUniqueInDB(){
        //given
        UserDTO existingUser = new UserDTO(999L, "existingUser", "password", LocalDateTime.now(), validPerson, validUserRole);
        UserDTO newUser = new UserDTO(999L, "newUser", "password", LocalDateTime.now(), validPerson, validUserRole);
        //when
        Mockito.when(userRepository.existsByUsername(existingUser.getUsername())).thenReturn(true);
        Mockito.when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        //then
        assertFalse(isUsernameValid(existingUser.getUsername()));
        assertTrue(isUsernameValid(newUser.getUsername()));
    }

    private boolean isUsernameValid(String username) {
        return generalValidationUtility.isNotNullAndNotBlank(username)
                && !userRepository.existsByUsername(username);
    }
}