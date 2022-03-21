package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class RecognitionResultControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RecognitionResultService service;

    @InjectMocks
    private RecognitionResultController controller;

    private static Gson gson;
    private static RecognitionResultDTO recognitionResult;

    @BeforeAll
    static void beforeAll() {
        gson = new Gson();
        recognitionResult = new RecognitionResultDTO(999L, "description ... ", 1, 1, null, null, null, null, null, null, null, null);
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
    void findAllRecognitionResults_shouldReturnRecognitionResultListAndStatusCode200_ifRecognitionResultsExist() throws Exception {
        // Given
        List<RecognitionResultDTO> recognitionResultList = Arrays.asList(
                recognitionResult,
                new RecognitionResultDTO(1000L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1001L, null, null, null, null, null, null, null, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        when(service.findAll(any())).thenReturn(new PageImpl<>(recognitionResultList, pageable, recognitionResultList.size()));
        // When
        // Then
        mockMvc.perform(get("/recognitionResult")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(recognitionResult)));
    }

    @Test
    void findAllRecognitionResultsGroupedByDate_shouldReturnResponseEntityOfFoundRecognitionResults_ifRecognitionResultsExist() throws Exception {
        // Given
        List<RecognitionResultDTO> recognitionResultList = Arrays.asList(
                recognitionResult,
                new RecognitionResultDTO(1000L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1001L, null, null, null, null, null, null, null, null, null, null, null));
        Map<LocalDateTime, List<RecognitionResultDTO>> resultMap = new HashMap<>();
        LocalDateTime currentDate = LocalDateTime.now();
        resultMap.put(currentDate, recognitionResultList);
        when(service.findAllGroupedByDate()).thenReturn(resultMap);
        // When
        // Then
        mockMvc.perform(get("/recognitionResult/groupByDate"))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(currentDate)))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(recognitionResult)));
    }

    @Test
    void findRecognitionResultsByUserId_shouldReturnResponseEntityOfFoundRecognitionResults_ifRecognitionResultsExist() throws Exception {
        // Given
        List<RecognitionResultDTO> recognitionResultList = Arrays.asList(
                recognitionResult,
                new RecognitionResultDTO(1000L, null, null, null, null, null, null, null, null, null, null, null),
                new RecognitionResultDTO(1001L, null, null, null, null, null, null, null, null, null, null, null));
        Long userId = 999L;
        when(service.findAllByUserId(userId)).thenReturn(recognitionResultList);
        // When
        // Then
        mockMvc.perform(get("/recognitionResult/user/{userId}", userId))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(recognitionResult)));
    }

    @Test
    void findItemById_shouldReturnRecognitionResultAndStatusCode200_ifRecognitionResultFoundById() throws Exception {
        // Given
        when(service.findById(recognitionResult.getRecognitionResultId())).thenReturn(recognitionResult);
        // When
        // Then
        mockMvc.perform(get("/recognitionResult/{id}", recognitionResult.getRecognitionResultId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(recognitionResult)));
    }

    @Test
    void findItemById_shouldReturnRecognitionResultAndStatusCode400_ifRecognitionResultNotFoundById() throws Exception {
        // Given
        when(service.findById(recognitionResult.getRecognitionResultId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/recognitionResult/{id}", recognitionResult.getRecognitionResultId()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(ERROR_MESSAGE_RECORD_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void createItem_shouldReturnRecognitionResultAndStatusCode201_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(recognitionResult)).thenReturn(recognitionResult);
        // When
        // Then
        mockMvc.perform(post("/recognitionResult")
                        .content(gson.toJson(recognitionResult))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(recognitionResult)));
    }

    @Test
    void updateItem_shouldReturnRecognitionResultAndStatusCode201_ifUpdateWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(recognitionResult)).thenReturn(recognitionResult);
        // When
        // Then
        mockMvc.perform(put("/recognitionResult")
                        .content(gson.toJson(recognitionResult))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(recognitionResult)));
    }

    @Test
    void deleteItem_shouldReturnRecognitionResultAndStatusCode200_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(recognitionResult.getRecognitionResultId())).thenReturn(recognitionResult);
        // When
        // Then
        mockMvc.perform(delete("/recognitionResult/{id}", recognitionResult.getRecognitionResultId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(recognitionResult)));
    }
}