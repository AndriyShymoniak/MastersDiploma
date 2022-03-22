package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.dto.*;
import com.nulp.shymoniak.mastersproject.utility.SecuritySessionUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DTOFieldFillerImplTest {
    @Mock
    private SecuritySessionUtility sessionUtility;

    @InjectMocks
    private DTOFieldFillerImpl fieldFiller;

    private static ApplicationUserDTO currentUser;

    private MediaDTO media;
    private MLModelDTO mlModel;
    private RecognitionResultDTO recognitionResult;
    private ApplicationUserDTO user;

    @BeforeAll
    static void beforeAll() {
        currentUser = new ApplicationUserDTO(999L, "Username", "password", null, null, null);
    }

    @BeforeEach
    void setUp() {
        media = new MediaDTO();
        mlModel = new MLModelDTO();
        recognitionResult = new RecognitionResultDTO();
        user = new ApplicationUserDTO();
        fieldFiller = null;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fillCreateFields_shouldFillCreateDateField_IfExists() {
        // Given
        when(sessionUtility.getCurrentUserFromSession()).thenReturn(currentUser);
        // When
        media = (MediaDTO) fieldFiller.fillCreateFields(media);
        mlModel = (MLModelDTO) fieldFiller.fillCreateFields(mlModel);
        recognitionResult = (RecognitionResultDTO) fieldFiller.fillCreateFields(recognitionResult);
        user = (ApplicationUserDTO) fieldFiller.fillCreateFields(user);
        // Then
        assertNotNull(media.getCreateDate());
        assertNotNull(mlModel.getCreateDate());
        assertNotNull(recognitionResult.getCreateDate());
        assertNotNull(user.getCreateDate());
        assertEquals(currentUser, media.getCreateUser());
        assertEquals(currentUser, mlModel.getCreateUser());
        assertEquals(currentUser, recognitionResult.getCreateUser());
    }

    @Test
    void fillUpdateFields_shouldFillUpdateDateField_IfExists() {
        // Given
        when(sessionUtility.getCurrentUserFromSession()).thenReturn(currentUser);
        // When
        recognitionResult = (RecognitionResultDTO) fieldFiller.fillUpdateFields(recognitionResult);
        // Then
        assertNotNull(recognitionResult.getUpdateDate());
        assertEquals(currentUser, recognitionResult.getUpdateUser());
    }
}