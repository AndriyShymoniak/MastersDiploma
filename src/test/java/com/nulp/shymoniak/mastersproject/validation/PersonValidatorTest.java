package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonValidatorTest {
    private final PersonValidator personValidator;

    @Autowired
    public PersonValidatorTest(PersonValidator personValidator) {
        this.personValidator = personValidator;
    }

    @Test
    void validatePersonName() {
        // given
        PersonDTO invalidPerson1 = new PersonDTO(999L, null, "Surname", "test@email.com");
        PersonDTO invalidPerson2 = new PersonDTO(999L, "", "Surname", "test@email.com");
        PersonDTO invalidPerson3 = new PersonDTO(999L, " ", "Surname", "test@email.com");
        PersonDTO validPerson1 = new PersonDTO(999L, "Anton", "Surname", "test@email.com");
        PersonDTO validPerson2 = new PersonDTO(999L, "Anna-Maria", "Surname", "test@email.com");
        // then
        assertFalse(personValidator.isValid(invalidPerson1));
        assertFalse(personValidator.isValid(invalidPerson2));
        assertFalse(personValidator.isValid(invalidPerson3));
        assertTrue(personValidator.isValid(validPerson1));
        assertTrue(personValidator.isValid(validPerson2));
    }

    @Test
    void validatePersonSurname() {
        // given
        PersonDTO invalidPerson1 = new PersonDTO(999L, "Name", null, "test@email.com");
        PersonDTO invalidPerson2 = new PersonDTO(999L, "Name", "", "test@email.com");
        PersonDTO invalidPerson3 = new PersonDTO(999L, "Name", "  ", "test@email.com");
        PersonDTO validPerson1 = new PersonDTO(999L, "Name", "Surname", "test@email.com");
        // then
        assertFalse(personValidator.isValid(invalidPerson1));
        assertFalse(personValidator.isValid(invalidPerson2));
        assertFalse(personValidator.isValid(invalidPerson3));
        assertTrue(personValidator.isValid(validPerson1));
    }

    @Test
    void validatePersonEmail() {
        // given
        PersonDTO invalidPerson1 = new PersonDTO(999L, "Name", "Surname", null);
        PersonDTO invalidPerson2 = new PersonDTO(999L, "Name", "Surname", "");
        PersonDTO invalidPerson3 = new PersonDTO(999L, "Name", "Surname", "  ");
        PersonDTO validPerson1 = new PersonDTO(999L, "Name", "Surname", "test@email.com");
        // then
        assertFalse(personValidator.isValid(invalidPerson1));
        assertFalse(personValidator.isValid(invalidPerson2));
        assertFalse(personValidator.isValid(invalidPerson3));
        assertTrue(personValidator.isValid(validPerson1));
    }
}