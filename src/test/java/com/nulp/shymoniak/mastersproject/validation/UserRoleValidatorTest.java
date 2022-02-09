package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRoleValidatorTest {
    private final UserRoleValidator userRoleValidator;

    @Autowired
    public UserRoleValidatorTest(UserRoleValidator userRoleValidator) {
        this.userRoleValidator = userRoleValidator;
    }

    @Test
    public void validateRole(){
        //given
        UserRoleDTO invalidUserRole1 = new UserRoleDTO(999L, null);
        UserRoleDTO invalidUserRole2 = new UserRoleDTO(999L, "");
        UserRoleDTO validUserRole1 = new UserRoleDTO(999L, "ROLE");
        //then
        assertFalse(userRoleValidator.isValid(invalidUserRole1));
        assertFalse(userRoleValidator.isValid(invalidUserRole2));
        assertTrue(userRoleValidator.isValid(validUserRole1));
    }
}