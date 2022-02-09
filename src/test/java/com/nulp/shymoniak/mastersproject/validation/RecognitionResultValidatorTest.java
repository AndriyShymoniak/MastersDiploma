package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.*;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecognitionResultValidatorTest {
    private final RecognitionResultValidator recognitionResultValidator;
    private final GeneralValidationUtility generalValidationUtility;

    @Mock
    private MLModelValidator mlModelValidator;

    @Mock
    private MediaValidator mediaValidator;

    @Mock
    private LocationValidator locationValidator;


    @Autowired
    public RecognitionResultValidatorTest(RecognitionResultValidator recognitionResultValidator, GeneralValidationUtility generalValidationUtility) {
        this.recognitionResultValidator = recognitionResultValidator;
        this.generalValidationUtility = generalValidationUtility;
    }

    @Test
    void validateRecognitionResultDescription() {
        // given
        RecognitionResultDTO invalidRecognitionResult1 = new RecognitionResultDTO(999L, null, 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        RecognitionResultDTO invalidRecognitionResult2 = new RecognitionResultDTO(999L, "", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        RecognitionResultDTO invalidRecognitionResult3 = new RecognitionResultDTO(999L, "   ", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        RecognitionResultDTO validRecognitionResult1 = new RecognitionResultDTO(999L, "Random description text", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        Mockito.when(mlModelValidator.isValid(Mockito.any())).thenReturn(true);
        Mockito.when(mediaValidator.isValid(Mockito.any())).thenReturn(true);
        Mockito.when(locationValidator.isValid(Mockito.any())).thenReturn(true);
        // then
        assertFalse(isValid(invalidRecognitionResult1));
        assertFalse(isValid(invalidRecognitionResult2));
        assertFalse(isValid(invalidRecognitionResult3));
        assertTrue(isValid(validRecognitionResult1));
    }

    private boolean isValid(RecognitionResultDTO recognitionResultDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(recognitionResultDTO.getDescription())
                && mlModelValidator.isValid(recognitionResultDTO.getMlModel())
                && mediaValidator.isValid(recognitionResultDTO.getMedia())
                && locationValidator.isValid(recognitionResultDTO.getLocation());
    }
}