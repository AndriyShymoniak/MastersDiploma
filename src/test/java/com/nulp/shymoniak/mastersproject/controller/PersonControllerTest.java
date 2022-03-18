package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private PersonService service;

    @InjectMocks
    private PersonController controller;

    private static Gson gson;
    private static PersonDTO person;

    @BeforeAll
    static void beforeAll() {
        gson = new Gson();
        person = new PersonDTO(999L, "Vitalii", "Kachmar", "vitalii_k@mail.com");
    }

    @BeforeEach
    void setUp() {
        controller = null;
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void findAllPersons_shouldReturnPersonListAndStatusCode200_ifPersonsExist() throws Exception {
        // Given
        List<PersonDTO> personList = Arrays.asList(
                person,
                new PersonDTO(1000L, null, null, null),
                new PersonDTO(1001L, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        when(service.findAll(any())).thenReturn(new PageImpl<>(personList, pageable, personList.size()));
        // When
        // Then
        mockMvc.perform(get("/person")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(person)));
    }

    @Test
    void findItemById_shouldReturnPersonAndStatusCode200_ifPersonFoundById() throws Exception {
        // Given
        when(service.findById(person.getPersonId())).thenReturn(person);
        // When
        // Then
        mockMvc.perform(get("/person/{id}", person.getPersonId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(person)));
    }

    @Test
    void findItemById_shouldReturnPersonAndStatusCode400_ifPersonNotFoundById() throws Exception {
        // Given
        when(service.findById(person.getPersonId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/person/{id}", person.getPersonId()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(ERROR_MESSAGE_RECORD_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void createItem_shouldReturnPersonAndStatusCode201_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(person)).thenReturn(person);
        // When
        // Then
        mockMvc.perform(post("/person")
                        .content(gson.toJson(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(person)));
    }

    @Test
    void updateItem_shouldReturnPersonAndStatusCode201_ifUpdateWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(person)).thenReturn(person);
        // When
        // Then
        mockMvc.perform(put("/person")
                        .content(gson.toJson(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(person)));
    }

    @Test
    void deleteItem_shouldReturnPersonAndStatusCode200_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(person.getPersonId())).thenReturn(person);
        // When
        // Then
        mockMvc.perform(delete("/person/{id}", person.getPersonId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(person)));
    }
}