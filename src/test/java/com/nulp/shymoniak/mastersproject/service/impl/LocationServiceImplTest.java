package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.entity.Location;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.LocationMapper;
import com.nulp.shymoniak.mastersproject.repository.LocationRepository;
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

    private Location location;
    private LocationDTO locationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        location = new Location(999L, "39.12345", "39.12345");
        locationDTO = new LocationDTO(999L, "39.12345", "39.12345");
    }

    @Test
    void findById_shouldReturnLocation_IfFinds() {
        // given
        when(repository.findById(location.getLocationId())).thenReturn(Optional.of(location));
        when(mapper.mapToDTO(location)).thenReturn(locationDTO);
        // when
        LocationDTO result = service.findById(location.getLocationId());
        // then
        verify(repository).findById(location.getLocationId());
        verify(mapper).mapToDTO(location);
        assertEquals(locationDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfNotFinds() {
        // given
        when(repository.findById(location.getLocationId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(location.getLocationId()));
        verify(repository).findById(location.getLocationId());
    }

    @Test
    void createItem_shouldSaveLocation() {
        // given
        when(mapper.mapToEntity(locationDTO)).thenReturn(location);
        when(mapper.mapToDTO(location)).thenReturn(locationDTO);
        // when
        LocationDTO result = service.createItem(locationDTO);
        // then
        verify(mapper).mapToEntity(locationDTO);
        verify(mapper).mapToDTO(location);
        verify(repository).save(location);
        assertEquals(locationDTO, result);
    }

    @Test
    void updateItem_shouldUpdateLocation_ifExist() {
        // given
        Location newLocationEntity = new Location(999L, "40.54321", "40.54321");
        LocationDTO newLocationDTO = new LocationDTO(999L, "40.54321", "40.54321");
        when(repository.existsById(location.getLocationId())).thenReturn(true);
        when(mapper.mapToEntity(newLocationDTO)).thenReturn(newLocationEntity);
        when(mapper.mapToDTO(newLocationEntity)).thenReturn(newLocationDTO);
        // when
        LocationDTO result = service.updateItem(newLocationDTO);
        // then
        verify(mapper).mapToEntity(newLocationDTO);
        verify(repository).existsById(location.getLocationId());
        verify(repository).save(newLocationEntity);
        assertEquals(newLocationDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(locationDTO)).thenReturn(location);
        when(repository.existsById(location.getLocationId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(locationDTO));
        verify(mapper).mapToEntity(locationDTO);
        verify(repository).existsById(location.getLocationId());
    }

    @Test
    void deleteItem_shouldDeleteLocation_IfFinds() {
        // given
        when(repository.findById(location.getLocationId())).thenReturn(Optional.of(location));
        when(mapper.mapToDTO(location)).thenReturn(locationDTO);
        // when
        LocationDTO result = service.deleteItem(location.getLocationId());
        // then
        verify(repository).findById(location.getLocationId());
        verify(repository).deleteById(location.getLocationId());
        assertEquals(locationDTO, result);
    }
}