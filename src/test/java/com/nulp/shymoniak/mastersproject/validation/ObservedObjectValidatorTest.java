package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ObservedObjectValidatorTest {
    private final ObservedObjectValidator observedObjectValidator;

    @Autowired
    public ObservedObjectValidatorTest(ObservedObjectValidator observedObjectValidator) {
        this.observedObjectValidator = observedObjectValidator;
    }

    @Test
    void validateObservedObjetName() {
        // Given
        ObservedObjectDTO invalidObservedObject1 = new ObservedObjectDTO(999L, null, null, null);
        ObservedObjectDTO invalidObservedObject2 = new ObservedObjectDTO(999L, "", null, null);
        ObservedObjectDTO invalidObservedObject3 = new ObservedObjectDTO(999L, " ", null, null);
        ObservedObjectDTO validObject1 = new ObservedObjectDTO(999L, "CAR", null, null);
        // Then
        assertFalse(observedObjectValidator.isValid(invalidObservedObject1));
        assertFalse(observedObjectValidator.isValid(invalidObservedObject2));
        assertFalse(observedObjectValidator.isValid(invalidObservedObject3));
        assertTrue(observedObjectValidator.isValid(validObject1));
    }
}