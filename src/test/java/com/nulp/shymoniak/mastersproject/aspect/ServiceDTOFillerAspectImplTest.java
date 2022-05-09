package com.nulp.shymoniak.mastersproject.aspect;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.aspect.impl.ServiceDTOFillerAspectImpl;
import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.service.impl.RecognitionResultServiceImpl;
import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import com.nulp.shymoniak.mastersproject.utility.DTOFieldFiller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ServiceDTOFillerAspectImplTest {
    @Mock
    private RecognitionResultRepository repository;

    @Mock
    private ApplicationUserRepository userRepository;

    @Mock
    private RecognitionResultMapper mapper;

    @Mock
    private AspectUtility aspectUtility;

    @Mock
    private DTOFieldFiller fieldFiller;

    @InjectMocks
    private RecognitionResultServiceImpl recognitionResultServiceImpl;

    @InjectMocks
    private ServiceDTOFillerAspectImpl aspectForService;

    private RecognitionResultService serviceProxy;

    private static LocalDateTime currentDate;
    private static ApplicationUser currentUserEntity;
    private static ApplicationUserDTO currentUserDTO;
    private static RecognitionResult recognitionResultEntity;
    private static RecognitionResult updatedRecognitionResultEntity;
    private static RecognitionResultDTO recognitionResultDTO;
    private static RecognitionResultDTO updatedRecognitionResultDTO;

    @BeforeAll
    static void beforeAll() {
        currentDate = LocalDateTime.now();
        currentUserEntity = new ApplicationUser();
        currentUserDTO = new ApplicationUserDTO();
        recognitionResultEntity = TestObjectsGenerator.generateRecognitionResult();
        recognitionResultDTO = TestObjectsGenerator.generateRecognitionResultDTO();

        updatedRecognitionResultEntity = TestObjectsGenerator.generateRecognitionResult();
        updatedRecognitionResultEntity.setCreateUser(currentUserEntity);
        updatedRecognitionResultEntity.setUpdateUser(currentUserEntity);
        updatedRecognitionResultEntity.setCreateDate(currentDate);
        updatedRecognitionResultEntity.setUpdateDate(currentDate);

        updatedRecognitionResultDTO = TestObjectsGenerator.generateRecognitionResultDTO();
        updatedRecognitionResultDTO.setCreateUser(currentUserDTO);
        updatedRecognitionResultDTO.setUpdateUser(currentUserDTO);
        updatedRecognitionResultDTO.setCreateDate(currentDate);
        updatedRecognitionResultDTO.setUpdateDate(currentDate);
    }

    @BeforeEach
    void setUp() {
        aspectForService = null;
        MockitoAnnotations.openMocks(this);
        AspectJProxyFactory aspectFactory = new AspectJProxyFactory(recognitionResultServiceImpl);
        aspectFactory.addAspect(aspectForService);
        serviceProxy = aspectFactory.getProxy();
    }

    @Test
    void fillDTOFieldsOnCreate_shouldFillCreateFields_whenCreateItemMethodCalled() {
        // Given
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(fieldFiller.fillCreateFields(recognitionResultDTO)).thenReturn(updatedRecognitionResultDTO);
        when(mapper.mapToEntity(updatedRecognitionResultDTO)).thenReturn(updatedRecognitionResultEntity);
        when(mapper.mapToDTO(updatedRecognitionResultEntity)).thenReturn(updatedRecognitionResultDTO);
        when(repository.save(updatedRecognitionResultEntity)).thenReturn(updatedRecognitionResultEntity);
        // When
        RecognitionResultDTO result = serviceProxy.createItem(recognitionResultDTO);
        // Then
        assertEquals(currentDate, result.getCreateDate());
        assertEquals(currentUserDTO, result.getCreateUser());
    }

    @Test
    void fillDTOFieldsOnUpdateOrDelete_shouldFillUpdateFields_whenUpdateItemMethodCalled() {
        // Given
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(fieldFiller.fillUpdateFields(recognitionResultDTO)).thenReturn(updatedRecognitionResultDTO);
        when(mapper.mapToEntity(updatedRecognitionResultDTO)).thenReturn(updatedRecognitionResultEntity);
        when(mapper.mapToDTO(updatedRecognitionResultEntity)).thenReturn(updatedRecognitionResultDTO);
        when(repository.existsById(recognitionResultEntity.getRecognitionResultId())).thenReturn(true);
        when(repository.save(recognitionResultEntity)).thenReturn(recognitionResultEntity);
        // When
        RecognitionResultDTO result = serviceProxy.updateItem(recognitionResultDTO);
        // Then
        assertEquals(currentDate, result.getUpdateDate());
        assertEquals(currentUserDTO, result.getUpdateUser());
    }

    // TODO: 3/14/22 create separate method for delete
    @Test
    void fillDTOFieldsOnUpdateOrDelete_shouldFillUpdateFields_whenDeleteItemMethodCalled() {
    }
}