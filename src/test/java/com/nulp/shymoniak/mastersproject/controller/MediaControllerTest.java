package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.MediaService;
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
class MediaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MediaService service;

    @InjectMocks
    private MediaController controller;

    private static Gson gson;
    private static MediaDTO media;

    @BeforeAll
    static void beforeAll() {
        gson = new Gson();
        media = TestObjectsGenerator.generateMediaDTO();
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
    void findAllMedias_shouldReturnMediaListAndStatusCode200_ifMediasExist() throws Exception {
        // Given
        List<MediaDTO> mediaList = TestObjectsGenerator.generateMediaDTOList();
        Pageable pageable = PageRequest.of(0, 10);
        when(service.findAll(any())).thenReturn(new PageImpl<>(mediaList, pageable, mediaList.size()));
        // When
        // Then
        mockMvc.perform(get("/media")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(media)));
    }

    @Test
    void findItemById_shouldReturnMediaAndStatusCode200_ifMediaFoundById() throws Exception {
        // Given
        when(service.findById(media.getMediaId())).thenReturn(media);
        // When
        // Then
        mockMvc.perform(get("/media/{id}", media.getMediaId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(media)));
    }

    @Test
    void findItemById_shouldReturnMediaAndStatusCode400_ifMediaNotFoundById() throws Exception {
        // Given
        when(service.findById(media.getMediaId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/media/{id}", media.getMediaId()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(ERROR_MESSAGE_RECORD_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void createItem_shouldReturnMediaAndStatusCode201_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(media)).thenReturn(media);
        // When
        // Then
        mockMvc.perform(post("/media")
                        .content(gson.toJson(media))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(media)));
    }

    @Test
    void updateItem_shouldReturnMediaAndStatusCode201_ifUpdateWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(media)).thenReturn(media);
        // When
        // Then
        mockMvc.perform(put("/media")
                        .content(gson.toJson(media))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(media)));
    }

    @Test
    void deleteItem_shouldReturnMediaAndStatusCode200_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(media.getMediaId())).thenReturn(media);
        // When
        // Then
        mockMvc.perform(delete("/media/{id}", media.getMediaId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(media)));
    }
}