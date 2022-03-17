package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecognitionResultControllerTest {
    @Mock
    private RecognitionResultService service;

    @InjectMocks
    private RecognitionResultController controller;

    private RecognitionResultDTO recognitionResult;

    @BeforeEach
    void setUp() {
        controller = null;
        recognitionResult = new RecognitionResultDTO(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllRecognitionResults_shouldReturnResponseEntityOfFoundRecognitionResults_ifRecognitionResultsExist() {
        // Given
        List<RecognitionResultDTO> recognitionResultList = Arrays.asList(
                new RecognitionResultDTO(1000L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1001L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1002L, null, null, null, null, null, null, null, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<RecognitionResultDTO> resultPage = new PageImpl<>(recognitionResultList, pageable, recognitionResultList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<RecognitionResultDTO>> requestResult = controller.findAllRecognitionResults(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findAllRecognitionResultsGroupedByDate_shouldReturnResponseEntityOfFoundRecognitionResults_ifRecognitionResultsExist() {
        // Given
        List<RecognitionResultDTO> recognitionResultList = Arrays.asList(
                new RecognitionResultDTO(1000L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1001L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1002L, null, null, null, null, null, null, null, null, null, null, null));
        Map<LocalDateTime, List<RecognitionResultDTO>> result = new HashMap<>();
        result.put(LocalDateTime.now(), recognitionResultList);
        when(service.findAllGroupedByDate()).thenReturn(result);
        // When
        ResponseEntity<Map<LocalDateTime, List<RecognitionResultDTO>>> requestResult = controller.findAllRecognitionResultsGroupedByDate();
        // Then
        verify(service).findAllGroupedByDate();
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(result));
    }

    @Test
    void findRecognitionResultsByUserId_shouldReturnResponseEntityOfFoundRecognitionResults_ifRecognitionResultsExist() {
        // Given
        Long randomUserId = 999L;
        List<RecognitionResultDTO> recognitionResultList = Arrays.asList(
                new RecognitionResultDTO(1000L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1001L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1002L, null, null, null, null, null, null, null, null, null, null, null));
        when(service.findAllByUserId(anyLong())).thenReturn(recognitionResultList);
        // When
        ResponseEntity<List<RecognitionResultDTO>> requestResult = controller.findRecognitionResultsByUserId(randomUserId);
        // Then
        verify(service).findAllByUserId(randomUserId);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(recognitionResultList));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundRecognitionResult_ifRecognitionResultExists() {
        // Given
        when(service.findById(recognitionResult.getRecognitionResultId())).thenReturn(recognitionResult);
        // When
        ResponseEntity<RecognitionResultDTO> requestResult = controller.findItemById(recognitionResult.getRecognitionResultId());
        // Then
        verify(service).findById(recognitionResult.getRecognitionResultId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(recognitionResult));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedRecognitionResult_ifCreationWasSuccessful() {
        // Given
        when(service.createItem(recognitionResult)).thenReturn(recognitionResult);
        // When
        ResponseEntity<RecognitionResultDTO> requestResult = controller.createItem(recognitionResult);
        // Then
        verify(service).createItem(recognitionResult);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(recognitionResult));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedRecognitionResult_ifUpdatingWasSuccessful() {
        // Given
        when(service.updateItem(recognitionResult)).thenReturn(recognitionResult);
        // When
        ResponseEntity<RecognitionResultDTO> requestResult = controller.updateItem(recognitionResult);
        // Then
        verify(service).updateItem(recognitionResult);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(recognitionResult));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedRecognitionResult_ifDeletionWasSuccessful() {
        // Given
        when(service.deleteItem(recognitionResult.getRecognitionResultId())).thenReturn(recognitionResult);
        // When
        ResponseEntity<RecognitionResultDTO> requestResult = controller.deleteItem(recognitionResult.getRecognitionResultId());
        // Then
        verify(service).deleteItem(recognitionResult.getRecognitionResultId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(recognitionResult));
    }
}