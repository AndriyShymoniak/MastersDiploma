package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.service.LocationService;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LocationControllerTest {
    @Mock
    private LocationService service;

    @InjectMocks
    private LocationController controller;

    private LocationDTO location;

    @BeforeEach
    void setUp() {
        controller = null;
        location = new LocationDTO(999L, "39.12345", "39.12345");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllLocations_shouldReturnResponseEntityOfFoundLocations_ifLocationsExist() {
        // Given
        List<LocationDTO> locationList = Arrays.asList(
                new LocationDTO(1000L, null, null),
                new LocationDTO(1001L, null, null),
                new LocationDTO(1002L, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<LocationDTO> resultPage = new PageImpl<>(locationList, pageable, locationList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<LocationDTO>> requestResult = controller.findAllLocations(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundLocation_ifLocationExists() {
        // Given
        when(service.findById(location.getLocationId())).thenReturn(location);
        // When
        ResponseEntity<LocationDTO> requestResult = controller.findItemById(location.getLocationId());
        // Then
        verify(service).findById(location.getLocationId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(location));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedLocation_ifCreationWasSuccessful() {
        // Given
        when(service.createItem(location)).thenReturn(location);
        // When
        ResponseEntity<LocationDTO> requestResult = controller.createItem(location);
        // Then
        verify(service).createItem(location);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(location));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedLocation_ifUpdatingWasSuccessful() {
        // Given
        when(service.updateItem(location)).thenReturn(location);
        // When
        ResponseEntity<LocationDTO> requestResult = controller.updateItem(location);
        // Then
        verify(service).updateItem(location);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(location));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedLocation_ifDeletionWasSuccessful() {
        // Given
        when(service.deleteItem(location.getLocationId())).thenReturn(location);
        // When
        ResponseEntity<LocationDTO> requestResult = controller.deleteItem(location.getLocationId());
        // Then
        verify(service).deleteItem(location.getLocationId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(location));
    }
}