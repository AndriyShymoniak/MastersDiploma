package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.entity.Person;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.PersonMapper;
import com.nulp.shymoniak.mastersproject.repository.PersonRepository;
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

    private Person person;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com");
        personDTO = new PersonDTO(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com");
    }

    @Test
    void findById_shouldReturnPerson_IfFinds() {
        // given
        when(repository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(mapper.mapToDTO(person)).thenReturn(personDTO);
        // when
        PersonDTO result = service.findById(person.getPersonId());
        // then
        verify(repository).findById(person.getPersonId());
        verify(mapper).mapToDTO(person);
        assertEquals(personDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // given
        when(repository.findById(person.getPersonId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(person.getPersonId()));
        verify(repository).findById(person.getPersonId());
    }

    @Test
    void createItem_shouldSavePerson() {
        // given
        when(mapper.mapToEntity(personDTO)).thenReturn(person);
        when(mapper.mapToDTO(person)).thenReturn(personDTO);
        // when
        PersonDTO result = service.createItem(personDTO);
        // then
        verify(mapper).mapToEntity(personDTO);
        verify(mapper).mapToDTO(person);
        verify(repository).save(person);
        assertEquals(personDTO, result);
    }

    @Test
    void updateItem_shouldUpdatePerson_ifExist() {
        // given
        Person newPersonEntity = new Person(999L, "NewName", "NewSurname", "vitalii_k@mail.com");
        PersonDTO newPersonDTO = new PersonDTO(999L, "NewName", "NewSurname", "vitalii_k@mail.com");
        when(repository.existsById(person.getPersonId())).thenReturn(true);
        when(mapper.mapToEntity(newPersonDTO)).thenReturn(newPersonEntity);
        when(mapper.mapToDTO(newPersonEntity)).thenReturn(newPersonDTO);
        // when
        PersonDTO result = service.updateItem(newPersonDTO);
        // then
        verify(mapper).mapToEntity(newPersonDTO);
        verify(repository).existsById(person.getPersonId());
        verify(repository).save(newPersonEntity);
        assertEquals(newPersonDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(personDTO)).thenReturn(person);
        when(repository.existsById(person.getPersonId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(personDTO));
        verify(mapper).mapToEntity(personDTO);
        verify(repository).existsById(person.getPersonId());
    }

    @Test
    void deleteItem_shouldDeletePerson_IfFinds() {
        // given
        when(repository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(mapper.mapToDTO(person)).thenReturn(personDTO);
        // when
        PersonDTO result = service.deleteItem(person.getPersonId());
        // then
        verify(repository).findById(person.getPersonId());
        verify(repository).deleteById(person.getPersonId());
        assertEquals(personDTO, result);
    }
}