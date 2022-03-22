package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.impl.LocationServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AspectUtilityImplTest {
    @Mock
    private JoinPoint joinPoint;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private AspectUtilityImpl aspectUtility;

    @Test
    void getInstanceOfClassWithJoinPoint_shouldReturnClassInstance_ifFound() {
        // Given
        when(joinPoint.getTarget()).thenReturn(locationService);
        // When
        Object result = aspectUtility.getInstanceOfClassWithJoinPoint(joinPoint);
        // Then
        assertEquals(locationService, result);
    }

    @Test
    void getInstanceOfClassWithJoinPoint_shouldThrowException_iflassInstanceNotFound() {
        // Given
        when(joinPoint.getTarget()).thenReturn(null);
        // When
        // Then
        assertThrows(Exception.class, () -> aspectUtility.getInstanceOfClassWithJoinPoint(joinPoint));

    }

    @Test
    void getDTOEntityFromParameters_shouldReturnDtoInstance_ifFound() {
        // Given
        LocationDTO expectedResult = new LocationDTO();
        Object[] arguments = {new Object(), 99L, expectedResult, new ArrayList<>()};
        when(joinPoint.getArgs()).thenReturn(arguments);
        // When
        Object result = aspectUtility.getDTOEntityFromParameters(joinPoint);
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void getDTOEntityFromParameters_shouldThrowException_ifDtoInstanceNotFound() {
        // Given
        Object[] arguments = {new Object(), 99L, new ArrayList<>()};
        when(joinPoint.getArgs()).thenReturn(arguments);
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> aspectUtility.getDTOEntityFromParameters(joinPoint));
    }
}