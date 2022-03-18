package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.ApplicationUserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ApplicationUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ApplicationUserService service;

    @InjectMocks
    private ApplicationUserController controller;

    private static Gson gson;
    private static ApplicationUserDTO user;

    @BeforeAll
    static void beforeAll() {
        gson = new Gson();
        user = new ApplicationUserDTO(999L, "Username", "password", null, null, null);
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
    void findAllUsers_shouldReturnApplicationUserListAndStatusCode200_ifUsersExist() throws Exception {
        // Given
        List<ApplicationUserDTO> userList = Arrays.asList(
                user,
                new ApplicationUserDTO(1000L, null, null, null, null, null),
                new ApplicationUserDTO(1001L, null, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        when(service.findAll(any())).thenReturn(new PageImpl<>(userList, pageable, userList.size()));
        // When
        // Then
        mockMvc.perform(get("/user")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString().contains(gson.toJson(user)));
    }

    @Test
    void findUserByUsername_shouldReturnApplicationUserAndStatusCode200_ifUserFoundByUsername() throws Exception {
        // Given
        when(service.findByUsername(user.getUsername())).thenReturn(user);
        // When
        // Then
        mockMvc.perform(get("/user/username/{username}", user.getUsername()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(user)));
    }

    @Test
    void findUserByUsername_shouldReturnApplicationUserAndStatusCode400_ifUserNotFoundByUsername() throws Exception {
        // Given
        when(service.findByUsername(user.getUsername())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/user/username/{username}", user.getUsername()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Test
    void findItemById_shouldReturnApplicationUserAndStatusCode200_ifUserFoundById() throws Exception {
        // Given
        when(service.findById(user.getUserId())).thenReturn(user);
        // When
        // Then
        mockMvc.perform(get("/user/{id}", user.getUserId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(user)));
    }

    @Test
    void findItemById_shouldReturnApplicationUserAndStatusCode400_ifUserNotFoundById() throws Exception {
        // Given
        when(service.findById(user.getUserId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/user/{id}", user.getUserId()))
                .andDo(log())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(ERROR_MESSAGE_RECORD_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void createItem_shouldReturnApplicationUserAndStatusCode201_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(user)).thenReturn(user);
        // When
        // Then
        mockMvc.perform(post("/user")
                        .content(gson.toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(user)));
    }

    @Test
    void updateItem_shouldReturnApplicationUserAndStatusCode201_ifUpdateWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(user)).thenReturn(user);
        // When
        // Then
        mockMvc.perform(put("/user")
                        .content(gson.toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(user)));
    }

    @Test
    void deleteItem_shouldReturnApplicationUserAndStatusCode200_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(user.getUserId())).thenReturn(user);
        // When
        // Then
        mockMvc.perform(delete("/user/{id}", user.getUserId()))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(user)));
    }
}