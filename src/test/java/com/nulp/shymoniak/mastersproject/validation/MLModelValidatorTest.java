package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MLModelValidatorTest {
    private final MLModelValidator mlModelValidator;
    private static String validURL;

    @Autowired
    public MLModelValidatorTest(MLModelValidator mlModelValidator) {
        this.mlModelValidator = mlModelValidator;
    }

    @BeforeAll
    static void setUp() {
        validURL = "https://www.baeldung.com/";
    }

    @Test
    void validateMLModelName() {
        // Given
        MLModelDTO invalidMLModel1 = new MLModelDTO(999L, null, validURL, 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        MLModelDTO invalidMLModel2 = new MLModelDTO(999L, "", validURL, 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        MLModelDTO invalidMLModel3 = new MLModelDTO(999L, " ", validURL, 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        MLModelDTO validMLModel1 = new MLModelDTO(999L, "NEW_MODEL", validURL, 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        // Then
        assertFalse(mlModelValidator.isValid(invalidMLModel1));
        assertFalse(mlModelValidator.isValid(invalidMLModel2));
        assertFalse(mlModelValidator.isValid(invalidMLModel3));
        assertTrue(mlModelValidator.isValid(validMLModel1));
    }

    @Test
    void validateMLModelPath() {
        // Given
        MLModelDTO invalidMLModel1 = new MLModelDTO(999L, "NEW_MODEL", null, 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        MLModelDTO invalidMLModel2 = new MLModelDTO(999L, "NEW_MODEL", "", 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        MLModelDTO invalidMLModel3 = new MLModelDTO(999L, "NEW_MODEL", "   ", 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        MLModelDTO validMLModel1 = new MLModelDTO(999L, "NEW_MODEL", validURL, 1, 1, LocalDateTime.now(), new ApplicationUserDTO(), new ArrayList<>());
        // Then
        assertFalse(mlModelValidator.isValid(invalidMLModel1));
        assertFalse(mlModelValidator.isValid(invalidMLModel2));
        assertFalse(mlModelValidator.isValid(invalidMLModel3));
        assertTrue(mlModelValidator.isValid(validMLModel1));
    }
}