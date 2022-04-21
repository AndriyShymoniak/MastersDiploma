package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecognitionResultServiceImplTest {
    @Mock
    private RecognitionResultRepository repository;

    @Mock
    private ApplicationUserRepository userRepository;

    @Mock
    private RecognitionResultMapper mapper;

    @InjectMocks
    private RecognitionResultServiceImpl service;

    private static RecognitionResult recognitionResult;
    private static RecognitionResultDTO recognitionResultDTO;

    @BeforeAll
    static void beforeAll() {
        recognitionResult = TestObjectsGenerator.generateRecognitionResult();
        recognitionResultDTO =TestObjectsGenerator.generateRecognitionResultDTO();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnRecognitionResult_IfFinds() {
        // Given
        when(repository.findById(recognitionResult.getRecognitionResultId())).thenReturn(Optional.of(recognitionResult));
        when(mapper.mapToDTO(recognitionResult)).thenReturn(recognitionResultDTO);
        // When
        RecognitionResultDTO result = service.findById(recognitionResult.getRecognitionResultId());
        // Then
        verify(repository).findById(recognitionResult.getRecognitionResultId());
        verify(mapper).mapToDTO(recognitionResult);
        assertEquals(recognitionResultDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // Given
        when(repository.findById(recognitionResult.getRecognitionResultId())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.findById(recognitionResult.getRecognitionResultId()));
        verify(repository).findById(recognitionResult.getRecognitionResultId());
    }

    @Test
    void createItem_shouldSaveRecognitionResult() {
        // Given
        when(mapper.mapToEntity(recognitionResultDTO)).thenReturn(recognitionResult);
        when(mapper.mapToDTO(recognitionResult)).thenReturn(recognitionResultDTO);
        when(repository.save(recognitionResult)).thenReturn(recognitionResult);
        // When
        RecognitionResultDTO result = service.createItem(recognitionResultDTO);
        // Then
        verify(mapper).mapToEntity(recognitionResultDTO);
        verify(mapper).mapToDTO(recognitionResult);
        verify(repository).save(recognitionResult);
        assertEquals(recognitionResultDTO, result);
    }

    @Test
    void updateItem_shouldUpdateRecognitionResult_ifExist() {
        // Given
        RecognitionResult newRecognitionResultEntity = TestObjectsGenerator.generateRecognitionResult();
        newRecognitionResultEntity.setDescription("new description ... ");
        RecognitionResultDTO newRecognitionResultDTO = TestObjectsGenerator.generateRecognitionResultDTO();
        newRecognitionResultDTO.setDescription("new description ... ");
        when(mapper.mapToEntity(newRecognitionResultDTO)).thenReturn(newRecognitionResultEntity);
        when(mapper.mapToDTO(newRecognitionResultEntity)).thenReturn(newRecognitionResultDTO);
        when(repository.existsById(recognitionResult.getRecognitionResultId())).thenReturn(true);
        when(repository.save(newRecognitionResultEntity)).thenReturn(newRecognitionResultEntity);
        // When
        RecognitionResultDTO result = service.updateItem(newRecognitionResultDTO);
        // Then
        verify(mapper).mapToEntity(newRecognitionResultDTO);
        verify(repository).existsById(recognitionResult.getRecognitionResultId());
        verify(repository).save(newRecognitionResultEntity);
        assertEquals(newRecognitionResultDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // Given
        when(mapper.mapToEntity(recognitionResultDTO)).thenReturn(recognitionResult);
        when(repository.existsById(recognitionResult.getRecognitionResultId())).thenReturn(false);
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.updateItem(recognitionResultDTO));
        verify(mapper).mapToEntity(recognitionResultDTO);
        verify(repository).existsById(recognitionResult.getRecognitionResultId());
    }

    @Test
    void deleteItem_shouldDeleteRecognitionResult_IfFinds() {
        // Given
        when(repository.findById(recognitionResult.getRecognitionResultId())).thenReturn(Optional.of(recognitionResult));
        when(mapper.mapToDTO(recognitionResult)).thenReturn(recognitionResultDTO);
        // When
        RecognitionResultDTO result = service.deleteItem(recognitionResult.getRecognitionResultId());
        // Then
        verify(repository).findById(recognitionResult.getRecognitionResultId());
        verify(repository).deleteById(recognitionResult.getRecognitionResultId());
        assertEquals(recognitionResultDTO, result);
    }

    @Test
    void findAllByUserId_shouldReturnRecognitionResultListWhichUserCreatedOrUpdated_ifSuchRecordsExist() {
        // Given
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS);
        List<RecognitionResult> expectedResultList = TestObjectsGenerator.generateRecognitionResultList(today, yesterday);
        List<RecognitionResultDTO> expectedResultDtoList = TestObjectsGenerator.generateRecognitionResultDTOList(today, yesterday);
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new ApplicationUser()));
        when(repository.findAllByCreateUserOrUpdateUser(any(), any())).thenReturn(expectedResultList);
        when(mapper.mapToDTO(expectedResultList)).thenReturn(expectedResultDtoList);
        // When
        List<RecognitionResultDTO> result = service.findAllByUserId(userId);
        // Then
        assertEquals(expectedResultDtoList, result);
    }

    @Test
    void findAllByUserId_shouldThrowException_IfUserDoesNotExist() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.findAllByUserId(userId));
    }

    @Test
    void findAllGroupedByDate_shouldReturnMapOfDateAndRecognitionResultList_whenCreateUserIdMatches() {
        // Given
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS);
        List<RecognitionResult> recognitionResults = TestObjectsGenerator.generateRecognitionResultList(today, yesterday);
        List<RecognitionResultDTO> recognitionResultDTOList = TestObjectsGenerator.generateRecognitionResultDTOList(today, yesterday);
        when(repository.findAllForList()).thenReturn(recognitionResults);
        when(mapper.mapToDTO(recognitionResults)).thenReturn(recognitionResultDTOList);
        // When
        Map<LocalDateTime, List<RecognitionResultDTO>> result = service.findAllGroupedByDate();
        // Then
        assertEquals(3, result.get(today).size());
        assertEquals(2, result.get(yesterday).size());
    }
}