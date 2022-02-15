package com.nulp.shymoniak.mastersproject.utility;

import com.nulp.shymoniak.mastersproject.dto.*;
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
        // given
        MediaDTO media = new MediaDTO();
        MLModelDTO mlModel = new MLModelDTO();
        RecognitionResultDTO recognitionResult = new RecognitionResultDTO();
        UserDTO user = new UserDTO();
        // when
        media = (MediaDTO) fieldFiller.fillCreateFields(media);
        mlModel = (MLModelDTO) fieldFiller.fillCreateFields(mlModel);
        recognitionResult = (RecognitionResultDTO) fieldFiller.fillCreateFields(recognitionResult);
        user = (UserDTO) fieldFiller.fillCreateFields(user);
        // then
        assertNotNull(media.getCreateDate());
        assertNotNull(mlModel.getCreateDate());
        assertNotNull(recognitionResult.getCreateDate());
        assertNotNull(user.getCreateDate());
    }

    @Test
    void fillUpdateFields_shouldFillUpdateDateField_IfExists() {
        // given
        RecognitionResultDTO recognitionResult = new RecognitionResultDTO();
        // when
        recognitionResult = (RecognitionResultDTO) fieldFiller.fillUpdateFields(recognitionResult);
        // then
        assertNotNull(recognitionResult.getUpdateDate());
    }
}