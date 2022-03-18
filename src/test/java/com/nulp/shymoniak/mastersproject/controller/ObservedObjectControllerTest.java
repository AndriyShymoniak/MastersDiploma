package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.ObservedObjectService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ObservedObjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ObservedObjectService service;

    @InjectMocks
    private ObservedObjectController controller;

    private static Gson gson;
    private static ObservedObjectDTO observedObject;

    @BeforeAll
    static void beforeAll() {
        gson = new Gson();
        observedObject = new ObservedObjectDTO(999L, "OBJ_NAME", null, null);
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
    void findAllObservedObjects_shouldReturnObservedObjectListAndStatusCode200_ifObservedObjectsExist() throws Exception {
        // Given
        List<ObservedObjectDTO> observedObjectList = Arrays.asList(
                observedObject,
                new ObservedObjectDTO(1000L, null, null, null),
                new ObservedObjectDTO(1001L, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        when(service.findAll(any())).thenReturn(new PageImpl<>(observedObjectList, pageable, observedObjectList.size()));
        // When
        // Then
        mockMvc.perform(get("/observedObject")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(observedObject)));

    }

    @Test
    void findItemById_shouldReturnObservedObjectAndStatusCode200_ifObservedObjectFoundById() throws Exception {
        // Given
        when(service.findById(observedObject.getObservedObjectId())).thenReturn(observedObject);
        // When
        // Then
        mockMvc.perform(get("/observedObject/{id}", observedObject.getObservedObjectId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(observedObject)));
    }

    @Test
    void findItemById_shouldReturnObservedObjectAndStatusCode400_ifObservedObjectNotFoundById() throws Exception {
        // Given
        when(service.findById(observedObject.getObservedObjectId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/observedObject/{id}", observedObject.getObservedObjectId()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(ERROR_MESSAGE_RECORD_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void createItem_shouldReturnObservedObjectAndStatusCode201_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(observedObject)).thenReturn(observedObject);
        // When
        // Then
        mockMvc.perform(post("/observedObject")
                        .content(gson.toJson(observedObject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(observedObject)));
    }

    @Test
    void updateItem_shouldReturnObservedObjectAndStatusCode201_ifUpdateWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(observedObject)).thenReturn(observedObject);
        // When
        // Then
        mockMvc.perform(put("/observedObject")
                        .content(gson.toJson(observedObject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(observedObject)));
    }

    @Test
    void deleteItem_shouldReturnObservedObjectAndStatusCode200_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(observedObject.getObservedObjectId())).thenReturn(observedObject);
        // When
        // Then
        mockMvc.perform(delete("/observedObject/{id}", observedObject.getObservedObjectId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(observedObject)));
    }
}