package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.dto.*;
import com.nulp.shymoniak.mastersproject.utility.DTOFieldFiller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DTOFieldFillerImplTest {
    private final DTOFieldFiller fieldFiller;

    @Autowired
    public DTOFieldFillerImplTest(DTOFieldFiller fieldFiller) {
        this.fieldFiller = fieldFiller;
    }

    @Test
    void fillCreateFields_shouldFillCreateDateField_IfExists() {
        // Given
        MediaDTO media = new MediaDTO();
        MLModelDTO mlModel = new MLModelDTO();
        RecognitionResultDTO recognitionResult = new RecognitionResultDTO();
        ApplicationUserDTO user = new ApplicationUserDTO();
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
    }

    @Test
    void fillUpdateFields_shouldFillUpdateDateField_IfExists() {
        // Given
        RecognitionResultDTO recognitionResult = new RecognitionResultDTO();
        // When
        recognitionResult = (RecognitionResultDTO) fieldFiller.fillUpdateFields(recognitionResult);
        // Then
        assertNotNull(recognitionResult.getUpdateDate());
    }
}