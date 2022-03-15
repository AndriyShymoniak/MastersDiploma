package com.nulp.shymoniak.mastersproject.aspect;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.service.impl.RecognitionResultServiceImpl;
import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import com.nulp.shymoniak.mastersproject.utility.DTOFieldFiller;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ServiceDTOFillerAspectImplTest {
    @Mock
    private RecognitionResultRepository repository;

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

    private RecognitionResult recognitionResultEntity;
    private RecognitionResult updatedRecognitionResultEntity;
    private RecognitionResultDTO recognitionResultDTO;
    private RecognitionResultDTO updatedRecognitionResultDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime currentDate = LocalDateTime.now();
        ApplicationUser currentUserEntity = new ApplicationUser();
        ApplicationUserDTO currentUserDTO = new ApplicationUserDTO();
        recognitionResultEntity = new RecognitionResult(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
        recognitionResultDTO = new RecognitionResultDTO(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
        updatedRecognitionResultEntity = new RecognitionResult(999L, "description ... ", 1, 1, currentDate, currentDate, null, null, null, currentUserEntity, currentUserEntity, null);
        updatedRecognitionResultDTO = new RecognitionResultDTO(999L, "description ... ", 1, 1, currentDate, currentDate, null, null, null, currentUserDTO, currentUserDTO, null);
        aspectForService = null;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fillDTOFieldsOnCreate_shouldFillCreateFields_whenCreateItemMethodCalled() {
        // given
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(fieldFiller.fillCreateFields(recognitionResultDTO)).thenReturn(updatedRecognitionResultDTO);
        when(mapper.mapToEntity(updatedRecognitionResultDTO)).thenReturn(updatedRecognitionResultEntity);
        when(mapper.mapToDTO(updatedRecognitionResultEntity)).thenReturn(updatedRecognitionResultDTO);
        AspectJProxyFactory factory = new AspectJProxyFactory(recognitionResultServiceImpl);
        factory.addAspect(aspectForService);
        RecognitionResultService serviceProxy = factory.getProxy();
        // when
        RecognitionResultDTO result = serviceProxy.createItem(recognitionResultDTO);
        // then
        assertTrue(result.getCreateDate() != null);
        assertTrue(result.getCreateUser() != null);
    }

    @Test
    void fillDTOFieldsOnUpdateOrDelete_shouldFillUpdateFields_whenUpdateItemMethodCalled() {
        // given
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(fieldFiller.fillUpdateFields(recognitionResultDTO)).thenReturn(updatedRecognitionResultDTO);
        when(repository.existsById(recognitionResultEntity.getRecognitionResultId())).thenReturn(true);
        when(mapper.mapToEntity(updatedRecognitionResultDTO)).thenReturn(updatedRecognitionResultEntity);
        when(mapper.mapToDTO(updatedRecognitionResultEntity)).thenReturn(updatedRecognitionResultDTO);
        AspectJProxyFactory factory = new AspectJProxyFactory(recognitionResultServiceImpl);
        factory.addAspect(aspectForService);
        RecognitionResultService serviceProxy = factory.getProxy();
        // when
        RecognitionResultDTO result = serviceProxy.updateItem(recognitionResultDTO);
        // then
        assertTrue(result.getUpdateDate() != null);
        assertTrue(result.getUpdateUser() != null);
    }

    // TODO: 3/14/22 create separate method for delete
    @Test
    void fillDTOFieldsOnUpdateOrDelete_shouldFillUpdateFields_whenDeleteItemMethodCalled() {
//        // given
//        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO.getRecognitionResultId());
//        when(fieldFiller.fillUpdateFields(recognitionResultDTO)).thenReturn(updatedRecognitionResultDTO);
//        when(repository.findById(updatedRecognitionResultEntity.getRecognitionResultId())).thenReturn(Optional.of(updatedRecognitionResultEntity));
//        when(mapper.mapToDTO(updatedRecognitionResultEntity)).thenReturn(updatedRecognitionResultDTO);
//        AspectJProxyFactory factory = new AspectJProxyFactory(recognitionResultServiceImpl);
//        factory.addAspect(aspectForService);
//        RecognitionResultService serviceProxy = factory.getProxy();
//        // when
//        RecognitionResultDTO result = serviceProxy.deleteItem(updatedRecognitionResultEntity.getRecognitionResultId());
//        // then
//        assertTrue(result.getUpdateDate() != null);
//        assertTrue(result.getUpdateUser() != null);
    }
}