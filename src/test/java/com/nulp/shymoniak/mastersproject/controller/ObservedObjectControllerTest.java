package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.service.ObservedObjectService;
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
class ObservedObjectControllerTest {
    @Mock
    private ObservedObjectService service;

    @InjectMocks
    private ObservedObjectController controller;

    private ObservedObjectDTO observedObject;

    @BeforeEach
    void setUp() {
        controller = null;
        observedObject = new ObservedObjectDTO(999L, "OBJ_NAME", null, null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllObservedObjects_shouldReturnResponseEntityOfFoundObservedObjects_ifObservedObjectsExist() {
        // Given
        List<ObservedObjectDTO> observedObjectList = Arrays.asList(
                new ObservedObjectDTO(1000L, null, null, null),
                new ObservedObjectDTO(1001L, null, null, null),
                new ObservedObjectDTO(1002L, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<ObservedObjectDTO> resultPage = new PageImpl<>(observedObjectList, pageable, observedObjectList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<ObservedObjectDTO>> requestResult = controller.findAllObservedObjects(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundObservedObject_ifObservedObjectExists() {
        // Given
        when(service.findById(observedObject.getObservedObjectId())).thenReturn(observedObject);
        // When
        ResponseEntity<ObservedObjectDTO> requestResult = controller.findItemById(observedObject.getObservedObjectId());
        // Then
        verify(service).findById(observedObject.getObservedObjectId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(observedObject));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedObservedObject_ifCreationWasSuccessful() {
        // Given
        when(service.createItem(observedObject)).thenReturn(observedObject);
        // When
        ResponseEntity<ObservedObjectDTO> requestResult = controller.createItem(observedObject);
        // Then
        verify(service).createItem(observedObject);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(observedObject));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedObservedObject_ifUpdatingWasSuccessful() {
        // Given
        when(service.updateItem(observedObject)).thenReturn(observedObject);
        // When
        ResponseEntity<ObservedObjectDTO> requestResult = controller.updateItem(observedObject);
        // Then
        verify(service).updateItem(observedObject);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(observedObject));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedObservedObject_ifDeletionWasSuccessful() {
        // Given
        when(service.deleteItem(observedObject.getObservedObjectId())).thenReturn(observedObject);
        // When
        ResponseEntity<ObservedObjectDTO> requestResult = controller.deleteItem(observedObject.getObservedObjectId());
        // Then
        verify(service).deleteItem(observedObject.getObservedObjectId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(observedObject));
    }
}