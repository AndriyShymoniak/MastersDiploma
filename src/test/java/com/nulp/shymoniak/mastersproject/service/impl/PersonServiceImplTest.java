package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.entity.Person;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.PersonMapper;
import com.nulp.shymoniak.mastersproject.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PersonRepository repository;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private PersonServiceImpl service;

    private static Person person;
    private static PersonDTO personDTO;

    @BeforeAll
    static void beforeAll() {
        person = new Person(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com", null);
        personDTO = new PersonDTO(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com", null);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnPerson_IfFinds() {
        // Given
        when(repository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(mapper.mapToDTO(person)).thenReturn(personDTO);
        // When
        PersonDTO result = service.findById(person.getPersonId());
        // Then
        verify(repository).findById(person.getPersonId());
        verify(mapper).mapToDTO(person);
        assertEquals(personDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // Given
        when(repository.findById(person.getPersonId())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.findById(person.getPersonId()));
        verify(repository).findById(person.getPersonId());
    }

    @Test
    void createItem_shouldSavePerson() {
        // Given
        when(mapper.mapToEntity(personDTO)).thenReturn(person);
        when(mapper.mapToDTO(person)).thenReturn(personDTO);
        // When
        PersonDTO result = service.createItem(personDTO);
        // Then
        verify(mapper).mapToEntity(personDTO);
        verify(mapper).mapToDTO(person);
        verify(repository).save(person);
        assertEquals(personDTO, result);
    }

    @Test
    void updateItem_shouldUpdatePerson_ifExist() {
        // Given
        Person newPersonEntity = new Person(999L, "NewName", "NewSurname", "vitalii_k@mail.com", null);
        PersonDTO newPersonDTO = new PersonDTO(999L, "NewName", "NewSurname", "vitalii_k@mail.com", null);
        when(repository.existsById(person.getPersonId())).thenReturn(true);
        when(mapper.mapToEntity(newPersonDTO)).thenReturn(newPersonEntity);
        when(mapper.mapToDTO(newPersonEntity)).thenReturn(newPersonDTO);
        // When
        PersonDTO result = service.updateItem(newPersonDTO);
        // Then
        verify(mapper).mapToEntity(newPersonDTO);
        verify(repository).existsById(person.getPersonId());
        verify(repository).save(newPersonEntity);
        assertEquals(newPersonDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // Given
        when(mapper.mapToEntity(personDTO)).thenReturn(person);
        when(repository.existsById(person.getPersonId())).thenReturn(false);
        // When
        // Then
        assertThrows(ApiRequestException.class, () -> service.updateItem(personDTO));
        verify(mapper).mapToEntity(personDTO);
        verify(repository).existsById(person.getPersonId());
    }

    @Test
    void deleteItem_shouldDeletePerson_IfFinds() {
        // Given
        when(repository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(mapper.mapToDTO(person)).thenReturn(personDTO);
        // When
        PersonDTO result = service.deleteItem(person.getPersonId());
        // Then
        verify(repository).findById(person.getPersonId());
        verify(repository).deleteById(person.getPersonId());
        assertEquals(personDTO, result);
    }
}