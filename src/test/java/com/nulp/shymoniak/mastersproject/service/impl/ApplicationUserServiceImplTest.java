package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.AbstractMapper;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApplicationUserServiceImplTest {
    @Mock
    private ApplicationUserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AbstractMapper mapper;

    @InjectMocks
    private ApplicationUserServiceImpl service;

    private static ApplicationUser user;
    private static ApplicationUserDTO userDTO;

    @BeforeAll
    static void beforeAll() {
        user = TestObjectsGenerator.generateApplicationUser();
        userDTO = TestObjectsGenerator.generateApplicationUserDTO();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnUser_IfFinds() {
        // Given
        when(repository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(mapper.mapToDTO(user)).thenReturn(userDTO);
        // When
        ApplicationUserDTO result = service.findById(user.getUserId());
        // Then
        verify(repository).findById(user.getUserId());
        verify(mapper).mapToDTO(user);
        assertEquals(userDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // Given
        when(repository.findById(user.getUserId())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.findById(user.getUserId()));
        verify(repository).findById(user.getUserId());
    }

    @Test
    void createItem_shouldSaveUser() {
        // Given
        when(mapper.mapToEntity(userDTO)).thenReturn(user);
        when(mapper.mapToDTO(user)).thenReturn(userDTO);
        when(repository.save(user)).thenReturn(user);
        // When
        ApplicationUserDTO result = service.createItem(userDTO);
        // Then
        verify(mapper).mapToEntity(userDTO);
        verify(mapper).mapToDTO(user);
        verify(repository).save(user);
        assertEquals(userDTO, result);
    }

    @Test
    void updateItem_shouldUpdateUser_ifExist() {
        // Given
        ApplicationUser newUserEntity = TestObjectsGenerator.generateApplicationUser();
        newUserEntity.setUsername("NewUser");
        ApplicationUserDTO newUserDTO = TestObjectsGenerator.generateApplicationUserDTO();
        newUserDTO.setUsername("NewUser");
        when(mapper.mapToEntity(newUserDTO)).thenReturn(newUserEntity);
        when(mapper.mapToDTO(newUserEntity)).thenReturn(newUserDTO);
        when(repository.existsById(user.getUserId())).thenReturn(true);
        when(repository.save(newUserEntity)).thenReturn(newUserEntity);
        // When
        ApplicationUserDTO result = service.updateItem(newUserDTO);
        // Then
        verify(mapper).mapToEntity(newUserDTO);
        verify(repository).existsById(user.getUserId());
        verify(repository).save(newUserEntity);
        assertEquals(newUserDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // Given
        when(mapper.mapToEntity(userDTO)).thenReturn(user);
        when(repository.existsById(user.getUserId())).thenReturn(false);
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.updateItem(userDTO));
        verify(mapper).mapToEntity(userDTO);
        verify(repository).existsById(user.getUserId());
    }

    @Test
    void deleteItem_shouldDeleteUser_IfFinds() {
        // Given
        when(repository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(mapper.mapToDTO(user)).thenReturn(userDTO);
        // When
        ApplicationUserDTO result = service.deleteItem(user.getUserId());
        // Then
        verify(repository).findById(user.getUserId());
        verify(repository).deleteById(user.getUserId());
        assertEquals(userDTO, result);
    }
}