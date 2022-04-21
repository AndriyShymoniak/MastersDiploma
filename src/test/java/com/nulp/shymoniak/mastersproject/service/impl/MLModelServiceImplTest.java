package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.MLModelMapper;
import com.nulp.shymoniak.mastersproject.repository.MLModelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MLModelServiceImplTest {
    @Mock
    private MLModelRepository repository;

    @Mock
    private MLModelMapper mapper;

    @Spy
    @InjectMocks
    private MLModelServiceImpl service;

    private static MLModel mlModel;
    private static MLModelDTO mlModelDTO;
    private static List<ObservedObject> observedObjectList;
    private static List<ObservedObjectDTO> observedObjectDTOList;
    private static List<MLModel> mlModelList;
    private static List<MLModelDTO> mlModelDTOList;

    @BeforeAll
    static void beforeAll() {
        mlModel = TestObjectsGenerator.generateMLModel();
        mlModelDTO = TestObjectsGenerator.generateMLModelDTO();
        observedObjectList = TestObjectsGenerator.generateObservedObjectList();
        observedObjectDTOList = TestObjectsGenerator.generateObservedObjectDTOList();
        mlModelList = TestObjectsGenerator.generateMLModelList();
        mlModelDTOList = TestObjectsGenerator.generateMLModelDTOList();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnMLModel_IfFinds() {
        // Given
        when(repository.findById(mlModel.getMlModelId())).thenReturn(Optional.of(mlModel));
        when(mapper.mapToDTO(mlModel)).thenReturn(mlModelDTO);
        // When
        MLModelDTO result = service.findById(mlModel.getMlModelId());
        // Then
        verify(repository).findById(mlModel.getMlModelId());
        verify(mapper).mapToDTO(mlModel);
        assertEquals(mlModelDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // Given
        when(repository.findById(mlModel.getMlModelId())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.findById(mlModel.getMlModelId()));
        verify(repository).findById(mlModel.getMlModelId());
    }

    @Test
    void createItem_shouldSaveMLModel() {
        // Given
        when(mapper.mapToEntity(mlModelDTO)).thenReturn(mlModel);
        when(mapper.mapToDTO(mlModel)).thenReturn(mlModelDTO);
        when(repository.save(mlModel)).thenReturn(mlModel);
        // When
        MLModelDTO result = service.createItem(mlModelDTO);
        // Then
        verify(mapper).mapToEntity(mlModelDTO);
        verify(mapper).mapToDTO(mlModel);
        verify(repository).save(mlModel);
        assertEquals(mlModelDTO, result);
    }

    @Test
    void updateItem_shouldUpdateMLModel_ifExist() {
        // Given
        MLModel newMLModelEntity = TestObjectsGenerator.generateMLModel();
        newMLModelEntity.setModelName("NEW_MODEL_NAME");
        MLModelDTO newMLModelDTO = TestObjectsGenerator.generateMLModelDTO();
        newMLModelDTO.setModelName("NEW_MODEL_NAME");
        when(mapper.mapToEntity(newMLModelDTO)).thenReturn(newMLModelEntity);
        when(mapper.mapToDTO(newMLModelEntity)).thenReturn(newMLModelDTO);
        when(repository.existsById(mlModel.getMlModelId())).thenReturn(true);
        when(repository.save(newMLModelEntity)).thenReturn(newMLModelEntity);
        // When
        MLModelDTO result = service.updateItem(newMLModelDTO);
        // Then
        verify(mapper).mapToEntity(newMLModelDTO);
        verify(repository).existsById(mlModel.getMlModelId());
        verify(repository).save(newMLModelEntity);
        assertEquals(newMLModelDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // Given
        when(mapper.mapToEntity(mlModelDTO)).thenReturn(mlModel);
        when(repository.existsById(mlModel.getMlModelId())).thenReturn(false);
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.updateItem(mlModelDTO));
        verify(mapper).mapToEntity(mlModelDTO);
        verify(repository).existsById(mlModel.getMlModelId());
    }

    @Test
    void deleteItem_shouldDeleteMLModel_IfFinds() {
        // Given
        when(repository.findById(mlModel.getMlModelId())).thenReturn(Optional.of(mlModel));
        when(mapper.mapToDTO(mlModel)).thenReturn(mlModelDTO);
        // When
        MLModelDTO result = service.deleteItem(mlModel.getMlModelId());
        // Then
        verify(repository).findById(mlModel.getMlModelId());
        verify(repository).deleteById(mlModel.getMlModelId());
        assertEquals(mlModelDTO, result);
    }

    @Test
    void findAllModelsByObservedObject_shouldReturnMLModels_ifTheyContainAllObservedObjects() {
        // Given
        Set<Long> observedObjectIdSet = new HashSet<>();

        List<MLModel> expectedTrueResultList = Stream.of(mlModelList.get(0), mlModelList.get(1), mlModelList.get(3)).collect(Collectors.toList());
        List<MLModelDTO> expectedTrueResultDtoList = Stream.of(mlModelDTOList.get(0), mlModelDTOList.get(1), mlModelDTOList.get(3)).collect(Collectors.toList());
        List<MLModel> expectedFalseResultList = Stream.of(mlModelList.get(2), mlModelList.get(4)).collect(Collectors.toList());
        List<MLModelDTO> expectedFalseResultDtoList = Stream.of(mlModelDTOList.get(2), mlModelDTOList.get(4)).collect(Collectors.toList());

        when(repository.findAllActiveModels()).thenReturn(mlModelList);
        when(mapper.mapToDTO(expectedTrueResultList)).thenReturn(expectedTrueResultDtoList);
        for (MLModel model : expectedTrueResultList) {
            doReturn(true).when(service).doesModelContainAllObservedObjects(model, observedObjectIdSet);
        }
        for (MLModel model : expectedFalseResultList) {
            doReturn(false).when(service).doesModelContainAllObservedObjects(model, observedObjectIdSet);
        }
        // When
        List<MLModelDTO> result = service.findAllModelsByObservedObject(observedObjectIdSet);
        // Then
        for (MLModelDTO model : expectedTrueResultDtoList) {
            assertTrue(result.contains(model));
        }
        for (MLModelDTO model : expectedFalseResultDtoList) {
            assertFalse(result.contains(model));
        }
    }

    @Test
    void doesModelContainAllObservedObjects_shouldReturnTrue_ifModelContainsAllObservedAbjects() {
        // Given
        Set<Long> observedObjectIdSet = Stream.of(
                observedObjectDTOList.get(0).getObservedObjectId(),
                observedObjectDTOList.get(1).getObservedObjectId()
        ).collect(Collectors.toSet());
        List<MLModel> expectedTrueResultList = Stream.of(mlModelList.get(0), mlModelList.get(1), mlModelList.get(3)).collect(Collectors.toList());
        // When
        Set<MLModel> result = mlModelList.stream()
                .filter(model -> service.doesModelContainAllObservedObjects(model, observedObjectIdSet))
                .collect(Collectors.toSet());
        // Then
        assertTrue(result.containsAll(expectedTrueResultList));
    }

    @Test
    void doesModelContainAllObservedObjects_shouldReturnFalse_ifModelDoesNotContainAllObservedAbjects() {
        // Given
        Set<Long> observedObjectIdSet = Stream.of(
                observedObjectDTOList.get(0).getObservedObjectId(),
                observedObjectDTOList.get(1).getObservedObjectId()
        ).collect(Collectors.toSet());
        List<MLModel> expectedFalseResultList = Stream.of(mlModelList.get(2), mlModelList.get(4)).collect(Collectors.toList());
        // When
        Set<MLModel> result = mlModelList.stream()
                .filter(model -> !service.doesModelContainAllObservedObjects(model, observedObjectIdSet))
                .collect(Collectors.toSet());
        // Then
        assertTrue(result.containsAll(expectedFalseResultList));
    }
}