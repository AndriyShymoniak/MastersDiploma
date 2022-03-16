package com.nulp.shymoniak.mastersproject.aspect;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.service.impl.RecognitionResultServiceImpl;
import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import com.nulp.shymoniak.mastersproject.validation.RecognitionResultValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ServiceValidatorAspectImplTest {
    @Mock
    private RecognitionResultRepository repository;

    @Mock
    private RecognitionResultMapper mapper;

    @Mock
    private RecognitionResultValidator validator;

    @Mock
    private AspectUtility aspectUtility;

    @InjectMocks
    private RecognitionResultServiceImpl recognitionResultServiceImpl;

    @InjectMocks
    private ServiceValidatorAspectImpl aspectForService;

    private RecognitionResult recognitionResultEntity;
    private RecognitionResultDTO recognitionResultDTO;

    @BeforeEach
    void setUp() {
        recognitionResultEntity = new RecognitionResult(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
        recognitionResultDTO = new RecognitionResultDTO(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
        aspectForService = null;
        validator = null;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateDTOs_shouldNotThrowException_whenDTOIsValid() {
        // given
        when(aspectUtility.getInstanceOfClassWithJoinPoint(any())).thenReturn(recognitionResultServiceImpl);
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(validator.isValid(any())).thenReturn(true);
        when(mapper.mapToEntity(recognitionResultDTO)).thenReturn(recognitionResultEntity);
        when(mapper.mapToDTO(recognitionResultEntity)).thenReturn(recognitionResultDTO);
        when(repository.existsById(recognitionResultEntity.getRecognitionResultId())).thenReturn(true);
        AspectJProxyFactory factory = new AspectJProxyFactory(recognitionResultServiceImpl);
        factory.addAspect(aspectForService);
        RecognitionResultService serviceProxy = factory.getProxy();
        // when
        // then
        serviceProxy.createItem(recognitionResultDTO);
        serviceProxy.updateItem(recognitionResultDTO);
    }

    @Test
    void validateDTOs_shouldThrowException_whenDTOIsInvalid() {
        // given
        when(aspectUtility.getInstanceOfClassWithJoinPoint(any())).thenReturn(recognitionResultServiceImpl);
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(validator.isValid(any())).thenReturn(false);
        AspectJProxyFactory factory = new AspectJProxyFactory(recognitionResultServiceImpl);
        factory.addAspect(aspectForService);
        RecognitionResultService serviceProxy = factory.getProxy();
        // when
        // then
        assertThrows(Exception.class, () -> serviceProxy.createItem(recognitionResultDTO));
        assertThrows(Exception.class, () -> serviceProxy.updateItem(recognitionResultDTO));
    }
}