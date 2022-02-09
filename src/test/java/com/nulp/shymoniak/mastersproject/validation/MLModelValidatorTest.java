package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MLModelValidatorTest {
    private final MLModelValidator mlModelValidator;
    private static UserDTO validUser;
    private static String validURL;
    private static List<MLModelObservedObjectDTO> validMlModelObservedObjectList;

    @Mock
    private ObservedObjectValidator observedObjectValidator;

    @Autowired
    public MLModelValidatorTest(MLModelValidator mlModelValidator) {
        this.mlModelValidator = mlModelValidator;
    }

    @BeforeAll
    static void setUp() {
        PersonDTO validPerson = new PersonDTO(999L, "Name", "Surname", "test@email.com");
        UserRoleDTO validUserRole = new UserRoleDTO(999L, "ROLE");
        ObservedObjectDTO validObservedObject1 = new ObservedObjectDTO(999L, "CAR", null, null);
        ObservedObjectDTO validObservedObject2 = new ObservedObjectDTO(999L, "PLANE", null, null);
        validURL = "https://www.baeldung.com/";
        validUser = new UserDTO(999L, "username", "password", LocalDateTime.now(), validPerson, validUserRole);
        validMlModelObservedObjectList = Stream.of(
                new MLModelObservedObjectDTO(999L, null, validObservedObject1),
                new MLModelObservedObjectDTO(999L, null, validObservedObject2)
        ).collect(Collectors.toList());
    }

    @Test
    void validateMLModelName() {
        // given
        MLModelDTO invalidMLModel1 = new MLModelDTO(999L, null, validURL, 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        MLModelDTO invalidMLModel2 = new MLModelDTO(999L, "", validURL, 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        MLModelDTO invalidMLModel3 = new MLModelDTO(999L, " ", validURL, 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        MLModelDTO validMLModel1 = new MLModelDTO(999L, "NEW_MODEL", validURL, 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        // then
        assertFalse(mlModelValidator.isValid(invalidMLModel1));
        assertFalse(mlModelValidator.isValid(invalidMLModel2));
        assertFalse(mlModelValidator.isValid(invalidMLModel3));
        assertTrue(mlModelValidator.isValid(validMLModel1));
    }

    @Test
    void validateMLModelPath() {
        // given
        MLModelDTO invalidMLModel1 = new MLModelDTO(999L, "NEW_MODEL", null, 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        MLModelDTO invalidMLModel2 = new MLModelDTO(999L, "NEW_MODEL", "", 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        MLModelDTO invalidMLModel3 = new MLModelDTO(999L, "NEW_MODEL", "   ", 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        MLModelDTO validMLModel1 = new MLModelDTO(999L, "NEW_MODEL", validURL, 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        // then
        assertFalse(mlModelValidator.isValid(invalidMLModel1));
        assertFalse(mlModelValidator.isValid(invalidMLModel2));
        assertFalse(mlModelValidator.isValid(invalidMLModel3));
        assertTrue(mlModelValidator.isValid(validMLModel1));
    }

    @Test
    void validateObservedObjectList() {
        // given
        MLModelDTO validMLModel1 = new MLModelDTO(999L, "NEW_MODEL", validURL, 1, 1, LocalDateTime.now(), validUser, validMlModelObservedObjectList);
        Mockito.when(observedObjectValidator.isValid(Mockito.any())).thenReturn(true);
        // when
        boolean result = isObservedObjectListValid(validMLModel1.getObservedObjectList());
        // then
        assertTrue(result);
    }

    private boolean isObservedObjectListValid(List<MLModelObservedObjectDTO> mlModelObservedObjectList) {
        return mlModelObservedObjectList.stream()
                .map(mlModelObservedObject -> mlModelObservedObject.getObservedObject())
                .allMatch(item -> observedObjectValidator.isValid(item));
    }
}