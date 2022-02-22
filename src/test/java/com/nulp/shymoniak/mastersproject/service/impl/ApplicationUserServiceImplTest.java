package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.UserMapper;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApplicationUserServiceImplTest {
    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;
    
    @InjectMocks
    private UserServiceImpl service;
    
    private ApplicationUser user;
    private ApplicationUserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new ApplicationUser(999L, "Username", "password", null, null, null);
        userDTO = new ApplicationUserDTO(999L, "Username", "password", null, null, null);
    }

    @Test
    void findById_shouldReturnUser_IfFinds() {
        // given
        when(repository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(mapper.mapToDTO(user)).thenReturn(userDTO);
        // when
        ApplicationUserDTO result = service.findById(user.getUserId());
        // then
        verify(repository).findById(user.getUserId());
        verify(mapper).mapToDTO(user);
        assertEquals(userDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // given
        when(repository.findById(user.getUserId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(user.getUserId()));
        verify(repository).findById(user.getUserId());
    }

    @Test
    void createItem_shouldSaveUser() {
        // given
        when(mapper.mapToEntity(userDTO)).thenReturn(user);
        when(mapper.mapToDTO(user)).thenReturn(userDTO);
        // when
        ApplicationUserDTO result = service.createItem(userDTO);
        // then
        verify(mapper).mapToEntity(userDTO);
        verify(mapper).mapToDTO(user);
        verify(repository).save(user);
        assertEquals(userDTO, result);
    }

    @Test
    void updateItem_shouldUpdateUser_ifExist() {
        // given
        ApplicationUser newUserEntity = new ApplicationUser(999L, "NewUsername", "password", null, null, null);
        ApplicationUserDTO newUserDTO = new ApplicationUserDTO(999L, "NewUsername", "password", null, null, null);
        when(repository.existsById(user.getUserId())).thenReturn(true);
        when(mapper.mapToEntity(newUserDTO)).thenReturn(newUserEntity);
        when(mapper.mapToDTO(newUserEntity)).thenReturn(newUserDTO);
        // when
        ApplicationUserDTO result = service.updateItem(newUserDTO);
        // then
        verify(mapper).mapToEntity(newUserDTO);
        verify(repository).existsById(user.getUserId());
        verify(repository).save(newUserEntity);
        assertEquals(newUserDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(userDTO)).thenReturn(user);
        when(repository.existsById(user.getUserId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(userDTO));
        verify(mapper).mapToEntity(userDTO);
        verify(repository).existsById(user.getUserId());
    }

    @Test
    void deleteItem_shouldDeleteUser_IfFinds() {
        // given
        when(repository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(mapper.mapToDTO(user)).thenReturn(userDTO);
        // when
        ApplicationUserDTO result = service.deleteItem(user.getUserId());
        // then
        verify(repository).findById(user.getUserId());
        verify(repository).deleteById(user.getUserId());
        assertEquals(userDTO, result);
    }
}