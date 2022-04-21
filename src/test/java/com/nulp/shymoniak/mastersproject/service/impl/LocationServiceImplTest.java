package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.entity.Location;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.LocationMapper;
import com.nulp.shymoniak.mastersproject.repository.LocationRepository;
import org.junit.jupiter.api.BeforeAll;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @Mock
    private LocationRepository repository;

    @Mock
    private LocationMapper mapper;

    @InjectMocks
    private LocationServiceImpl service;

    private static Location location;
    private static LocationDTO locationDTO;

    @BeforeAll
    static void beforeAll() {
        location = TestObjectsGenerator.generateLocation();
        locationDTO = TestObjectsGenerator.generateLocationDTO();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnLocation_IfFinds() {
        // Given
        when(repository.findById(location.getLocationId())).thenReturn(Optional.of(location));
        when(mapper.mapToDTO(location)).thenReturn(locationDTO);
        // When
        LocationDTO result = service.findById(location.getLocationId());
        // Then
        verify(repository).findById(location.getLocationId());
        verify(mapper).mapToDTO(location);
        assertEquals(locationDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // Given
        when(repository.findById(location.getLocationId())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.findById(location.getLocationId()));
        verify(repository).findById(location.getLocationId());
    }

    @Test
    void createItem_shouldSaveLocation() {
        // Given
        when(mapper.mapToEntity(locationDTO)).thenReturn(location);
        when(mapper.mapToDTO(location)).thenReturn(locationDTO);
        when(repository.save(location)).thenReturn(location);
        // When
        LocationDTO result = service.createItem(locationDTO);
        // Then
        verify(mapper).mapToEntity(locationDTO);
        verify(mapper).mapToDTO(location);
        verify(repository).save(location);
        assertEquals(locationDTO, result);
    }

    @Test
    void updateItem_shouldUpdateLocation_ifExist() {
        // Given
        Location newLocationEntity = TestObjectsGenerator.generateLocation();
        newLocationEntity.setLatitude("99.9999");
        LocationDTO newLocationDTO = TestObjectsGenerator.generateLocationDTO();
        newLocationDTO.setLatitude("99.9999");
        when(mapper.mapToEntity(newLocationDTO)).thenReturn(newLocationEntity);
        when(mapper.mapToDTO(newLocationEntity)).thenReturn(newLocationDTO);
        when(repository.existsById(location.getLocationId())).thenReturn(true);
        when(repository.save(newLocationEntity)).thenReturn(newLocationEntity);
        // When
        LocationDTO result = service.updateItem(newLocationDTO);
        // Then
        verify(mapper).mapToEntity(newLocationDTO);
        verify(repository).existsById(location.getLocationId());
        verify(repository).save(newLocationEntity);
        assertEquals(newLocationDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // Given
        when(mapper.mapToEntity(locationDTO)).thenReturn(location);
        when(repository.existsById(location.getLocationId())).thenReturn(false);
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.updateItem(locationDTO));
        verify(mapper).mapToEntity(locationDTO);
        verify(repository).existsById(location.getLocationId());
    }

    @Test
    void deleteItem_shouldDeleteLocation_IfFinds() {
        // Given
        when(repository.findById(location.getLocationId())).thenReturn(Optional.of(location));
        when(mapper.mapToDTO(location)).thenReturn(locationDTO);
        // When
        LocationDTO result = service.deleteItem(location.getLocationId());
        // Then
        verify(repository).findById(location.getLocationId());
        verify(repository).deleteById(location.getLocationId());
        assertEquals(locationDTO, result);
    }
}