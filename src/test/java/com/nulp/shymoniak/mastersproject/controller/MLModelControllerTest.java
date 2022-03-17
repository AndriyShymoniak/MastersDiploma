package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MLModelControllerTest {
    @Mock
    private MLModelService service;

    @InjectMocks
    private MLModelController controller;

    private MLModelDTO mlModel;

    @BeforeEach
    void setUp() {
        controller = null;
        mlModel = new MLModelDTO(999L, "MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllMLModels_shouldReturnResponseEntityOfFoundMLModels_ifMLModelsExist() {
        // Given
        List<MLModelDTO> mlModelList = Arrays.asList(
                new MLModelDTO(1000L, null, null, null, null, null, null, null),
                new MLModelDTO(1001L, null, null, null, null, null, null, null),
                new MLModelDTO(1002L, null, null, null, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<MLModelDTO> resultPage = new PageImpl<>(mlModelList, pageable, mlModelList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<MLModelDTO>> requestResult = controller.findAllModels(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findAllModelsByObservedObject_shouldReturnResponseEntityOfFoundMLModels_ifMLModelsExist() {
        // Given
        Set observedObjIdSet = Stream.of(1000L, 1001L, 1002L).collect(Collectors.toSet());
        List<MLModelDTO> mlModelList = Arrays.asList(
                new MLModelDTO(1000L, null, null, null, null, null, null, null),
                new MLModelDTO(1001L, null, null, null, null, null, null, null),
                new MLModelDTO(1002L, null, null, null, null, null, null, null));
        when(service.findAllModelsByObservedObject(observedObjIdSet)).thenReturn(mlModelList);
        // When
        ResponseEntity<List<MLModelDTO>> requestResult = controller.findAllModelsByObservedObject(observedObjIdSet);
        // Then
        verify(service).findAllModelsByObservedObject(observedObjIdSet);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(mlModelList));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundMLModel_ifMLModelExists() {
        // Given
        when(service.findById(mlModel.getMlModelId())).thenReturn(mlModel);
        // When
        ResponseEntity<MLModelDTO> requestResult = controller.findItemById(mlModel.getMlModelId());
        // Then
        verify(service).findById(mlModel.getMlModelId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(mlModel));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedMLModel_ifCreationWasSuccessful() {
        // Given
        when(service.createItem(mlModel)).thenReturn(mlModel);
        // When
        ResponseEntity<MLModelDTO> requestResult = controller.createItem(mlModel);
        // Then
        verify(service).createItem(mlModel);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(mlModel));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedMLModel_ifUpdatingWasSuccessful() {
        // Given
        when(service.updateItem(mlModel)).thenReturn(mlModel);
        // When
        ResponseEntity<MLModelDTO> requestResult = controller.updateItem(mlModel);
        // Then
        verify(service).updateItem(mlModel);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(mlModel));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedMLModel_ifDeletionWasSuccessful() {
        // Given
        when(service.deleteItem(mlModel.getMlModelId())).thenReturn(mlModel);
        // When
        ResponseEntity<MLModelDTO> requestResult = controller.deleteItem(mlModel.getMlModelId());
        // Then
        verify(service).deleteItem(mlModel.getMlModelId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(mlModel));
    }
}