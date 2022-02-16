package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.entity.User;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// TODO: 2/16/22 finish test
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecognitionResultServiceImplTest {
    @Mock
    private RecognitionResultRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecognitionResultMapper mapper;

    @InjectMocks
    private RecognitionResultServiceImpl service;

    private RecognitionResult recognitionResult;
    private RecognitionResultDTO recognitionResultDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recognitionResult = new RecognitionResult(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
        recognitionResultDTO = new RecognitionResultDTO(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
    }

    @Test
    void findById_shouldReturnRecognitionResult_IfFinds() {
        // given
        when(repository.findById(recognitionResult.getRecognitionResultId())).thenReturn(Optional.of(recognitionResult));
        when(mapper.mapToDTO(recognitionResult)).thenReturn(recognitionResultDTO);
        // when
        RecognitionResultDTO result = service.findById(recognitionResult.getRecognitionResultId());
        // then
        verify(repository).findById(recognitionResult.getRecognitionResultId());
        verify(mapper).mapToDTO(recognitionResult);
        assertEquals(recognitionResultDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // given
        when(repository.findById(recognitionResult.getRecognitionResultId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(recognitionResult.getRecognitionResultId()));
        verify(repository).findById(recognitionResult.getRecognitionResultId());
    }

    @Test
    void createItem_shouldSaveRecognitionResult() {
        // given
        when(mapper.mapToEntity(recognitionResultDTO)).thenReturn(recognitionResult);
        when(mapper.mapToDTO(recognitionResult)).thenReturn(recognitionResultDTO);
        // when
        RecognitionResultDTO result = service.createItem(recognitionResultDTO);
        // then
        verify(mapper).mapToEntity(recognitionResultDTO);
        verify(mapper).mapToDTO(recognitionResult);
        verify(repository).save(recognitionResult);
        assertEquals(recognitionResultDTO, result);
    }

    @Test
    void updateItem_shouldUpdateRecognitionResult_ifExist() {
        // given
        RecognitionResult newRecognitionResultEntity = new RecognitionResult(999L, "new description ... ", 1, 1, null, null, null, null, null, null, null, null);
        RecognitionResultDTO newRecognitionResultDTO = new RecognitionResultDTO(999L, "new description ... ", 1, 1, null, null, null, null, null, null, null, null);
        when(repository.existsById(recognitionResult.getRecognitionResultId())).thenReturn(true);
        when(mapper.mapToEntity(newRecognitionResultDTO)).thenReturn(newRecognitionResultEntity);
        when(mapper.mapToDTO(newRecognitionResultEntity)).thenReturn(newRecognitionResultDTO);
        // when
        RecognitionResultDTO result = service.updateItem(newRecognitionResultDTO);
        // then
        verify(mapper).mapToEntity(newRecognitionResultDTO);
        verify(repository).existsById(recognitionResult.getRecognitionResultId());
        verify(repository).save(newRecognitionResultEntity);
        assertEquals(newRecognitionResultDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(recognitionResultDTO)).thenReturn(recognitionResult);
        when(repository.existsById(recognitionResult.getRecognitionResultId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(recognitionResultDTO));
        verify(mapper).mapToEntity(recognitionResultDTO);
        verify(repository).existsById(recognitionResult.getRecognitionResultId());
    }

    @Test
    void deleteItem_shouldDeleteRecognitionResult_IfFinds() {
        // given
        when(repository.findById(recognitionResult.getRecognitionResultId())).thenReturn(Optional.of(recognitionResult));
        when(mapper.mapToDTO(recognitionResult)).thenReturn(recognitionResultDTO);
        // when
        RecognitionResultDTO result = service.deleteItem(recognitionResult.getRecognitionResultId());
        // then
        verify(repository).findById(recognitionResult.getRecognitionResultId());
        verify(repository).deleteById(recognitionResult.getRecognitionResultId());
        assertEquals(recognitionResultDTO, result);
    }

//    @Test
//    void findAllByUserId_shouldThrowException_IfUserDoNotExist() {
//        // given
//        List<User> userList = generateTestUserListValues();
//        for (User user : userList) {
//            when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(new User()));
//        }
//        when(repository.findAll()).thenReturn(generateTestRecognitionResultListValues(generateTestUserListValues()));
//        when(userRepository.findAll()).thenReturn(userList);
//        // when
//        // then
//        assertThrows(ApiRequestException.class, () -> service.findAllByUserId(userList.get(0).getUserId()));
//        assertThrows(ApiRequestException.class, () -> service.findAllByUserId(userList.get(1).getUserId()));
//        assertThrows(ApiRequestException.class, () -> service.findAllByUserId(userList.get(2).getUserId()));
//        verify(userRepository).findById(userList.get(0).getUserId());
//        verify(userRepository).findById(userList.get(1).getUserId());
//        verify(userRepository).findById(userList.get(2).getUserId());
//    }
//
//    @Test
//    void findAllGroupedByDate_shouldReturnRecognitionResultList_whenUpdateUserIdMatches() {
//    }


    private List<User> generateTestUserListValues() {
        return Stream.of(
                new User(1000L, "Username1", "password", null, null, null),
                new User(1001L, "Username2", "password", null, null, null),
                new User(1002L, "Username3", "password", null, null, null)
        ).collect(Collectors.toList());
    }

    private List<UserDTO> generateTestUserDTOListValues() {
        return Stream.of(
                new UserDTO(1000L, "Username1", "password", null, null, null),
                new UserDTO(1001L, "Username2", "password", null, null, null),
                new UserDTO(1002L, "Username3", "password", null, null, null)
        ).collect(Collectors.toList());
    }

    private List<RecognitionResult> generateTestRecognitionResultListValues(List<User> users) {
        return Stream.of(
                new RecognitionResult(1001L, "description ...", 1, 1, null, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResult(1002L, "description ...", 1, 1, null, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResult(1003L, "description ...", 1, 1, null, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResult(1004L, "description ...", 1, 1, null, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResult(1005L, "description ...", 1, 1, null, null, null, null, null, users.get(1), users.get(2), null)
        ).collect(Collectors.toList());
    }

    private List<RecognitionResultDTO> generateTestRecognitionResultDTOListValues(List<UserDTO> users) {
        return Stream.of(
                new RecognitionResultDTO(1001L, "description ...", 1, 1, null, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResultDTO(1002L, "description ...", 1, 1, null, null, null, null, null, users.get(0), users.get(1), null),
                new RecognitionResultDTO(1003L, "description ...", 1, 1, null, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResultDTO(1004L, "description ...", 1, 1, null, null, null, null, null, users.get(1), users.get(2), null),
                new RecognitionResultDTO(1005L, "description ...", 1, 1, null, null, null, null, null, users.get(1), users.get(2), null)
        ).collect(Collectors.toList());
    }
}