package com.nulp.shymoniak.mastersproject.aspect;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.aspect.impl.ServiceValidatorAspectImpl;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.service.impl.RecognitionResultServiceImpl;
import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import com.nulp.shymoniak.mastersproject.validation.RecognitionResultValidator;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ServiceValidatorAspectImplTest {
    @Mock
    private RecognitionResultRepository repository;

    @Mock
    private ApplicationUserRepository userRepository;

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

    private RecognitionResultService serviceProxy;

    private static RecognitionResult recognitionResultEntity;
    private static RecognitionResultDTO recognitionResultDTO;

    @BeforeAll
    static void beforeAll() {
        recognitionResultEntity = TestObjectsGenerator.generateRecognitionResult();
        recognitionResultDTO = TestObjectsGenerator.generateRecognitionResultDTO();
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
    void validateDTOs_shouldNotThrowException_whenDTOIsValid() {
        // Given
        when(aspectUtility.getInstanceOfClassWithJoinPoint(any())).thenReturn(recognitionResultServiceImpl);
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(validator.isValid(any())).thenReturn(true);
        when(mapper.mapToEntity(recognitionResultDTO)).thenReturn(recognitionResultEntity);
        when(mapper.mapToDTO(recognitionResultEntity)).thenReturn(recognitionResultDTO);
        when(repository.existsById(recognitionResultEntity.getRecognitionResultId())).thenReturn(true);
        when(repository.save(recognitionResultEntity)).thenReturn(recognitionResultEntity);
        // When
        // Then
        serviceProxy.createItem(recognitionResultDTO);
        serviceProxy.updateItem(recognitionResultDTO);
    }

    @Test
    void validateDTOs_shouldThrowException_whenDTOIsInvalid() {
        // Given
        when(aspectUtility.getInstanceOfClassWithJoinPoint(any())).thenReturn(recognitionResultServiceImpl);
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(recognitionResultDTO);
        when(validator.isValid(any())).thenReturn(false);
        // When
        // Then
        assertThrows(Exception.class, () -> serviceProxy.createItem(recognitionResultDTO));
        assertThrows(Exception.class, () -> serviceProxy.updateItem(recognitionResultDTO));
    }
}