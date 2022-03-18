package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.LocationService;
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
class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LocationService service;

    @InjectMocks
    private LocationController controller;

    private static Gson gson;
    private static LocationDTO location;

    @BeforeAll
    static void beforeAll() {
        gson = new Gson();
        location = new LocationDTO(999L, "39.12345", "39.12345");
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
    void findAllLocations_shouldReturnLocationListAndStatusCode200_ifLocationsExist() throws Exception {
        // Given
        List<LocationDTO> locationList = Arrays.asList(
                location,
                new LocationDTO(1000L, null, null),
                new LocationDTO(1001L, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        when(service.findAll(any())).thenReturn(new PageImpl<>(locationList, pageable, locationList.size()));
        // When
        // Then
        mockMvc.perform(get("/location")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(location)));
    }

    @Test
    void findItemById_shouldReturnLocationAndStatusCode200_ifLocationFoundById() throws Exception {
        // Given
        when(service.findById(location.getLocationId())).thenReturn(location);
        // When
        // Then
        mockMvc.perform(get("/location/{id}", location.getLocationId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(location)));
    }

    @Test
    void findItemById_shouldReturnLocationAndStatusCode400_ifLocationNotFoundById() throws Exception {
        // Given
        when(service.findById(location.getLocationId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/location/{id}", location.getLocationId()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(ERROR_MESSAGE_RECORD_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void createItem_shouldReturnLocationAndStatusCode201_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(location)).thenReturn(location);
        // When
        // Then
        mockMvc.perform(post("/location")
                        .content(gson.toJson(location))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(location)));
    }

    @Test
    void updateItem_shouldReturnLocationAndStatusCode201_ifUpdateWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(location)).thenReturn(location);
        // When
        // Then
        mockMvc.perform(put("/location")
                        .content(gson.toJson(location))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(location)));
    }

    @Test
    void deleteItem_shouldReturnLocationAndStatusCode200_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(location.getLocationId())).thenReturn(location);
        // When
        // Then
        mockMvc.perform(delete("/location/{id}", location.getLocationId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(location)));
    }
}