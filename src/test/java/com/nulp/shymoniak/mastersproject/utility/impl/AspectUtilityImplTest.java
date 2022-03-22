package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.service.impl.LocationServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
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
    void getDTOEntityFromParameters() {
        // Given
        LocationDTO expectedResult = new LocationDTO();
        Object[] arguments = {new Object(), 99L, expectedResult, new ArrayList<>()};
        when(joinPoint.getArgs()).thenReturn(arguments);
        // When
        Object result = aspectUtility.getDTOEntityFromParameters(joinPoint);
        // Then
        assertEquals(expectedResult, result);
    }
}