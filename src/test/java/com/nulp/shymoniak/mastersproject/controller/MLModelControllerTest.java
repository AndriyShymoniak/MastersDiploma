package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
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
class MLModelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MLModelService service;

    @InjectMocks
    private MLModelController controller;

    private static Gson gson;
    private static MLModelDTO mlModel;

    @BeforeAll
    static void beforeAll() {
        gson = new Gson();
        mlModel = new MLModelDTO(999L, "MODEL_NAME", "https://github.com/", 1, 1, null, null, null);
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
    void findAllMLModels_shouldReturnMLModelListAndStatusCode200_ifMLModelsExist() throws Exception {
        // Given
        List<MLModelDTO> mlModelList = Arrays.asList(
                mlModel,
                new MLModelDTO(1000L, null, null, null, null, null, null, null),
                new MLModelDTO(1001L, null, null, null, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        when(service.findAll(any())).thenReturn(new PageImpl<>(mlModelList, pageable, mlModelList.size()));
        // When
        // Then
        mockMvc.perform(get("/mlModel")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(mlModel)));
    }

    @Test
    void findAllModelsByObservedObject_shouldReturnMLModelListAndStatusCode200_ifMLModelsExist() throws Exception {
        // Given
        List<MLModelDTO> mlModelList = Arrays.asList(
                mlModel,
                new MLModelDTO(1000L, null, null, null, null, null, null, null),
                new MLModelDTO(1001L, null, null, null, null, null, null, null));
        when(service.findAllModelsByObservedObject(any())).thenReturn(mlModelList);
        // When
        // Then
        mockMvc.perform(get("/mlModel/observedObject")
                        .param("observedObjectIdSet", ""))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(mlModel)));
    }

    @Test
    void findItemById_shouldReturnMLModelAndStatusCode200_ifMLModelFoundById() throws Exception {
        // Given
        when(service.findById(mlModel.getMlModelId())).thenReturn(mlModel);
        // When
        // Then
        mockMvc.perform(get("/mlModel/{id}", mlModel.getMlModelId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(mlModel)));
    }
    
    @Test
    void findItemById_shouldReturnMLModelAndStatusCode400_ifMLModelNotFoundById() throws Exception {
        // Given
        when(service.findById(mlModel.getMlModelId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/mlModel/{id}", mlModel.getMlModelId()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(ERROR_MESSAGE_RECORD_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void createItem_shouldReturnMLModelAndStatusCode201_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(mlModel)).thenReturn(mlModel);
        // When
        // Then
        mockMvc.perform(post("/mlModel")
                        .content(gson.toJson(mlModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(mlModel)));
    }

    @Test
    void updateItem_shouldReturnMLModelAndStatusCode201_ifUpdateWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(mlModel)).thenReturn(mlModel);
        // When
        // Then
        mockMvc.perform(put("/mlModel")
                        .content(gson.toJson(mlModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(mlModel)));
    }

    @Test
    void deleteItem_shouldReturnMLModelAndStatusCode200_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(mlModel.getMlModelId())).thenReturn(mlModel);
        // When
        // Then
        mockMvc.perform(delete("/mlModel/{id}", mlModel.getMlModelId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(mlModel)));
    }
}