package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {
    @Mock
    private PersonService service;

    @InjectMocks
    private PersonController controller;

    private PersonDTO person;

    @BeforeEach
    void setUp() {
        controller = null;
        person = new PersonDTO(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllPersons_shouldReturnResponseEntityOfFoundPersons_ifPersonsExist() {
        // Given
        List<PersonDTO> personList = Arrays.asList(
                new PersonDTO(1000L, null, null, null),
                new PersonDTO(1001L, null, null, null),
                new PersonDTO(1002L, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<PersonDTO> resultPage = new PageImpl<>(personList, pageable, personList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<PersonDTO>> requestResult = controller.findAllPersons(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundPerson_ifPersonExists() {
        // Given
        when(service.findById(person.getPersonId())).thenReturn(person);
        // When
        ResponseEntity<PersonDTO> requestResult = controller.findItemById(person.getPersonId());
        // Then
        verify(service).findById(person.getPersonId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(person));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedPerson_ifCreationWasSuccessful() {
        // Given
        when(service.createItem(person)).thenReturn(person);
        // When
        ResponseEntity<PersonDTO> requestResult = controller.createItem(person);
        // Then
        verify(service).createItem(person);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(person));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedPerson_ifUpdatingWasSuccessful() {
        // Given
        when(service.updateItem(person)).thenReturn(person);
        // When
        ResponseEntity<PersonDTO> requestResult = controller.updateItem(person);
        // Then
        verify(service).updateItem(person);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(person));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedPerson_ifDeletionWasSuccessful() {
        // Given
        when(service.deleteItem(person.getPersonId())).thenReturn(person);
        // When
        ResponseEntity<PersonDTO> requestResult = controller.deleteItem(person.getPersonId());
        // Then
        verify(service).deleteItem(person.getPersonId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(person));
    }
}