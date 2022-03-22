package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.service.ApplicationUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SecuritySessionUtilityImplTest {
    @Mock
    private ApplicationUserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private SecuritySessionUtilityImpl sessionUtility;

    private static ApplicationUserDTO currentUser;

    @BeforeAll
    static void beforeAll() {
        currentUser = new ApplicationUserDTO(999L, "Username", "password", null, null, null);
    }

    @BeforeEach
    void setUp() {
        sessionUtility = null;
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getCurrentUserFromSession() {
        // Given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(currentUser.getUsername());
        when(userService.findByUsername(currentUser.getUsername())).thenReturn(currentUser);
        // When
        ApplicationUserDTO result = sessionUtility.getCurrentUserFromSession();
        // Then
        assertEquals(currentUser, result);
    }
}