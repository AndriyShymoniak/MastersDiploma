package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecognitionResultValidatorTest {
    private final RecognitionResultValidator recognitionResultValidator;

    @Autowired
    public RecognitionResultValidatorTest(RecognitionResultValidator recognitionResultValidator) {
        this.recognitionResultValidator = recognitionResultValidator;
    }

    @Test
    void validateRecognitionResultDescription() {
        // Given
        RecognitionResultDTO invalidRecognitionResult1 = new RecognitionResultDTO(999L, null, 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new ApplicationUserDTO(), null, null);
        RecognitionResultDTO invalidRecognitionResult2 = new RecognitionResultDTO(999L, "", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new ApplicationUserDTO(), null, null);
        RecognitionResultDTO invalidRecognitionResult3 = new RecognitionResultDTO(999L, "   ", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new ApplicationUserDTO(), null, null);
        RecognitionResultDTO validRecognitionResult1 = new RecognitionResultDTO(999L, "Random description text", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new ApplicationUserDTO(), null, null);
        // Then
        assertFalse(recognitionResultValidator.isValid(invalidRecognitionResult1));
        assertFalse(recognitionResultValidator.isValid(invalidRecognitionResult2));
        assertFalse(recognitionResultValidator.isValid(invalidRecognitionResult3));
        assertTrue(recognitionResultValidator.isValid(validRecognitionResult1));
    }
}