package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.ObservedObjectMapper;
import com.nulp.shymoniak.mastersproject.repository.ObservedObjectRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ObservedObjectServiceImplTest {
    @Mock
    private ObservedObjectRepository repository;
    
    @Mock
    private ObservedObjectMapper mapper;
    
    @InjectMocks
    private ObservedObjectServiceImpl service;
    
    private static ObservedObject observedObject;
    private static ObservedObjectDTO observedObjectDTO;

    @BeforeAll
    static void beforeAll() {
        observedObject = TestObjectsGenerator.generateObservedObject();
        observedObjectDTO = TestObjectsGenerator.generateObservedObjectDTO();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnObservedObject_IfFinds() {
        // Given
        when(repository.findById(observedObject.getObservedObjectId())).thenReturn(Optional.of(observedObject));
        when(mapper.mapToDTO(observedObject)).thenReturn(observedObjectDTO);
        // When
        ObservedObjectDTO result = service.findById(observedObject.getObservedObjectId());
        // Then
        verify(repository).findById(observedObject.getObservedObjectId());
        verify(mapper).mapToDTO(observedObject);
        assertEquals(observedObjectDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // Given
        when(repository.findById(observedObject.getObservedObjectId())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.findById(observedObject.getObservedObjectId()));
        verify(repository).findById(observedObject.getObservedObjectId());
    }

    @Test
    void createItem_shouldSaveObservedObject() {
        // Given
        when(mapper.mapToEntity(observedObjectDTO)).thenReturn(observedObject);
        when(mapper.mapToDTO(observedObject)).thenReturn(observedObjectDTO);
        when(repository.save(observedObject)).thenReturn(observedObject);
        // When
        ObservedObjectDTO result = service.createItem(observedObjectDTO);
        // Then
        verify(mapper).mapToEntity(observedObjectDTO);
        verify(mapper).mapToDTO(observedObject);
        verify(repository).save(observedObject);
        assertEquals(observedObjectDTO, result);
    }

    @Test
    void updateItem_shouldUpdateObservedObject_ifExist() {
        // Given
        ObservedObject newObservedObjectEntity = TestObjectsGenerator.generateObservedObject();
        newObservedObjectEntity.setObjectName("NEW_OBJ_NAME");
        ObservedObjectDTO newObservedObjectDTO = TestObjectsGenerator.generateObservedObjectDTO();
        newObservedObjectDTO.setObjectName("NEW_OBJ_NAME");
        when(mapper.mapToEntity(newObservedObjectDTO)).thenReturn(newObservedObjectEntity);
        when(mapper.mapToDTO(newObservedObjectEntity)).thenReturn(newObservedObjectDTO);
        when(repository.existsById(observedObject.getObservedObjectId())).thenReturn(true);
        when(repository.save(newObservedObjectEntity)).thenReturn(newObservedObjectEntity);
        // When
        ObservedObjectDTO result = service.updateItem(newObservedObjectDTO);
        // Then
        verify(mapper).mapToEntity(newObservedObjectDTO);
        verify(repository).existsById(observedObject.getObservedObjectId());
        verify(repository).save(newObservedObjectEntity);
        assertEquals(newObservedObjectDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // Given
        when(mapper.mapToEntity(observedObjectDTO)).thenReturn(observedObject);
        when(repository.existsById(observedObject.getObservedObjectId())).thenReturn(false);
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.updateItem(observedObjectDTO));
        verify(mapper).mapToEntity(observedObjectDTO);
        verify(repository).existsById(observedObject.getObservedObjectId());
    }

    @Test
    void deleteItem_shouldDeleteObservedObject_IfFinds() {
        // Given
        when(repository.findById(observedObject.getObservedObjectId())).thenReturn(Optional.of(observedObject));
        when(mapper.mapToDTO(observedObject)).thenReturn(observedObjectDTO);
        // When
        ObservedObjectDTO result = service.deleteItem(observedObject.getObservedObjectId());
        // Then
        verify(repository).findById(observedObject.getObservedObjectId());
        verify(repository).deleteById(observedObject.getObservedObjectId());
        assertEquals(observedObjectDTO, result);
    }
}