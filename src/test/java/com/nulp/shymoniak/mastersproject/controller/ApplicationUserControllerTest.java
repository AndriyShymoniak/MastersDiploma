package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.service.ApplicationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApplicationUserControllerTest {
    @Mock
    private ApplicationUserService service;

    @InjectMocks
    private ApplicationUserController controller;

    private ApplicationUserDTO user;

    @BeforeEach
    void setUp() {
        controller = null;
        user = new ApplicationUserDTO(999L, "Username", "password", null, null, null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllUsers_shouldReturnResponseEntityOfFoundUsers_ifUsersExist() {
        // Given
        List<ApplicationUserDTO> userList = Arrays.asList(
                new ApplicationUserDTO(1000L, null, null, null, null, null),
                new ApplicationUserDTO(1001L, null, null, null, null, null),
                new ApplicationUserDTO(1002L, null, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<ApplicationUserDTO> resultPage = new PageImpl<>(userList, pageable, userList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<ApplicationUserDTO>> requestResult = controller.findAllUsers(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findUserByUsername_shouldReturnResponseEntityOfFoundUsers_ifUsersExist() {
        // Given
        when(service.findByUsername(user.getUsername())).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.findUserByUsername(user.getUsername());
        // Then
        verify(service).findByUsername(user.getUsername());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(user));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundUser_ifUserExists() {
        // Given
        when(service.findById(user.getUserId())).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.findItemById(user.getUserId());
        // Then
        verify(service).findById(user.getUserId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(user));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedUser_ifCreationWasSuccessful() {
        // Given
        when(service.createItem(user)).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.createItem(user);
        // Then
        verify(service).createItem(user);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(user));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedUser_ifUpdatingWasSuccessful() {
        // Given
        when(service.updateItem(user)).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.updateItem(user);
        // Then
        verify(service).updateItem(user);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(user));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedUser_ifDeletionWasSuccessful() {
        // Given
        when(service.deleteItem(user.getUserId())).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.deleteItem(user.getUserId());
        // Then
        verify(service).deleteItem(user.getUserId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(user));
    }
}