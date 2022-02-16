package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.entity.UserRole;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.UserRoleMapper;
import com.nulp.shymoniak.mastersproject.repository.UserRoleRepository;
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
class UserRoleServiceImplTest {
    @Mock
    private UserRoleRepository repository;

    @Mock
    private UserRoleMapper mapper;
    
    @InjectMocks
    private UserRoleServiceImpl service;
    
    private UserRole userRole;
    private UserRoleDTO userRoleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRole = new UserRole(999L, "SUPER_USER");
        userRoleDTO = new UserRoleDTO(999L, "SUPER_USER");
    }

    @Test
    void findById_shouldReturnUserRole_IfFinds() {
        // given
        when(repository.findById(userRole.getUserRoleId())).thenReturn(Optional.of(userRole));
        when(mapper.mapToDTO(userRole)).thenReturn(userRoleDTO);
        // when
        UserRoleDTO result = service.findById(userRole.getUserRoleId());
        // then
        verify(repository).findById(userRole.getUserRoleId());
        verify(mapper).mapToDTO(userRole);
        assertEquals(userRoleDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // given
        when(repository.findById(userRole.getUserRoleId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(userRole.getUserRoleId()));
        verify(repository).findById(userRole.getUserRoleId());
    }

    @Test
    void createItem_shouldSaveUserRole() {
        // given
        when(mapper.mapToEntity(userRoleDTO)).thenReturn(userRole);
        when(mapper.mapToDTO(userRole)).thenReturn(userRoleDTO);
        // when
        UserRoleDTO result = service.createItem(userRoleDTO);
        // then
        verify(mapper).mapToEntity(userRoleDTO);
        verify(mapper).mapToDTO(userRole);
        verify(repository).save(userRole);
        assertEquals(userRoleDTO, result);
    }

    @Test
    void updateItem_shouldUpdateUserRole_ifExist() {
        // given
        UserRole newUserRoleEntity = new UserRole(999L, "NEW_SUPER_USER");
        UserRoleDTO newUserRoleDTO = new UserRoleDTO(999L, "NEW_SUPER_USER");
        when(repository.existsById(userRole.getUserRoleId())).thenReturn(true);
        when(mapper.mapToEntity(newUserRoleDTO)).thenReturn(newUserRoleEntity);
        when(mapper.mapToDTO(newUserRoleEntity)).thenReturn(newUserRoleDTO);
        // when
        UserRoleDTO result = service.updateItem(newUserRoleDTO);
        // then
        verify(mapper).mapToEntity(newUserRoleDTO);
        verify(repository).existsById(userRole.getUserRoleId());
        verify(repository).save(newUserRoleEntity);
        assertEquals(newUserRoleDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(userRoleDTO)).thenReturn(userRole);
        when(repository.existsById(userRole.getUserRoleId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(userRoleDTO));
        verify(mapper).mapToEntity(userRoleDTO);
        verify(repository).existsById(userRole.getUserRoleId());
    }

    @Test
    void deleteItem_shouldDeleteUserRole_IfFinds() {
        // given
        when(repository.findById(userRole.getUserRoleId())).thenReturn(Optional.of(userRole));
        when(mapper.mapToDTO(userRole)).thenReturn(userRoleDTO);
        // when
        UserRoleDTO result = service.deleteItem(userRole.getUserRoleId());
        // then
        verify(repository).findById(userRole.getUserRoleId());
        verify(repository).deleteById(userRole.getUserRoleId());
        assertEquals(userRoleDTO, result);
    }
}