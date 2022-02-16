package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.ObservedObjectMapper;
import com.nulp.shymoniak.mastersproject.repository.ObservedObjectRepository;
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
    
    private ObservedObject observedObject;
    private ObservedObjectDTO observedObjectDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        observedObject = new ObservedObject(999L, "OBJ_NAME", null, null);
        observedObjectDTO = new ObservedObjectDTO(999L, "OBJ_NAME", null, null);
    }

    @Test
    void findById_shouldReturnObservedObject_IfFinds() {
        // given
        when(repository.findById(observedObject.getObservedObjectId())).thenReturn(Optional.of(observedObject));
        when(mapper.mapToDTO(observedObject)).thenReturn(observedObjectDTO);
        // when
        ObservedObjectDTO result = service.findById(observedObject.getObservedObjectId());
        // then
        verify(repository).findById(observedObject.getObservedObjectId());
        verify(mapper).mapToDTO(observedObject);
        assertEquals(observedObjectDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // given
        when(repository.findById(observedObject.getObservedObjectId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(observedObject.getObservedObjectId()));
        verify(repository).findById(observedObject.getObservedObjectId());
    }

    @Test
    void createItem_shouldSaveObservedObject() {
        // given
        when(mapper.mapToEntity(observedObjectDTO)).thenReturn(observedObject);
        when(mapper.mapToDTO(observedObject)).thenReturn(observedObjectDTO);
        // when
        ObservedObjectDTO result = service.createItem(observedObjectDTO);
        // then
        verify(mapper).mapToEntity(observedObjectDTO);
        verify(mapper).mapToDTO(observedObject);
        verify(repository).save(observedObject);
        assertEquals(observedObjectDTO, result);
    }

    @Test
    void updateItem_shouldUpdateObservedObject_ifExist() {
        // given
        ObservedObject newObservedObjectEntity = new ObservedObject(999L, "NEW_OBJ_NAME", null, null);
        ObservedObjectDTO newObservedObjectDTO = new ObservedObjectDTO(999L, "NEW_OBJ_NAME", null, null);
        when(repository.existsById(observedObject.getObservedObjectId())).thenReturn(true);
        when(mapper.mapToEntity(newObservedObjectDTO)).thenReturn(newObservedObjectEntity);
        when(mapper.mapToDTO(newObservedObjectEntity)).thenReturn(newObservedObjectDTO);
        // when
        ObservedObjectDTO result = service.updateItem(newObservedObjectDTO);
        // then
        verify(mapper).mapToEntity(newObservedObjectDTO);
        verify(repository).existsById(observedObject.getObservedObjectId());
        verify(repository).save(newObservedObjectEntity);
        assertEquals(newObservedObjectDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(observedObjectDTO)).thenReturn(observedObject);
        when(repository.existsById(observedObject.getObservedObjectId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(observedObjectDTO));
        verify(mapper).mapToEntity(observedObjectDTO);
        verify(repository).existsById(observedObject.getObservedObjectId());
    }

    @Test
    void deleteItem_shouldDeleteObservedObject_IfFinds() {
        // given
        when(repository.findById(observedObject.getObservedObjectId())).thenReturn(Optional.of(observedObject));
        when(mapper.mapToDTO(observedObject)).thenReturn(observedObjectDTO);
        // when
        ObservedObjectDTO result = service.deleteItem(observedObject.getObservedObjectId());
        // then
        verify(repository).findById(observedObject.getObservedObjectId());
        verify(repository).deleteById(observedObject.getObservedObjectId());
        assertEquals(observedObjectDTO, result);
    }
}