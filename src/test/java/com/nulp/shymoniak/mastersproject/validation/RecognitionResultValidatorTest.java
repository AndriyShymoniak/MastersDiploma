package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class RecognitionResultValidatorTest {
    private final RecognitionResultValidator recognitionResultValidator;

    @Test
    void validateRecognitionResultDescription() {
        // given
        RecognitionResultDTO invalidRecognitionResult1 = new RecognitionResultDTO(999L, null, 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        RecognitionResultDTO invalidRecognitionResult2 = new RecognitionResultDTO(999L, "", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        RecognitionResultDTO invalidRecognitionResult3 = new RecognitionResultDTO(999L, "   ", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        RecognitionResultDTO validRecognitionResult1 = new RecognitionResultDTO(999L, "Random description text", 1, 1, LocalDateTime.now(), null, new MLModelDTO(), new MediaDTO(), new LocationDTO(), new UserDTO(), null, null);
        // then
        assertFalse(recognitionResultValidator.isValid(invalidRecognitionResult1));
        assertFalse(recognitionResultValidator.isValid(invalidRecognitionResult2));
        assertFalse(recognitionResultValidator.isValid(invalidRecognitionResult3));
        assertTrue(recognitionResultValidator.isValid(validRecognitionResult1));
    }
}