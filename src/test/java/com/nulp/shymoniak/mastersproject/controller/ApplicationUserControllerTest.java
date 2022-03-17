package com.nulp.shymoniak.mastersproject.controller;

import com.google.gson.Gson;
import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.exception.ApiExceptionHandler;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.service.ApplicationUserService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ApplicationUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ApplicationUserService service;

    @InjectMocks
    private ApplicationUserController controller;

    private ApplicationUserDTO user;
    private Gson gson;

    @BeforeEach
    void setUp() {
        controller = null;
        user = new ApplicationUserDTO(999L, "Username", "password", null, null, null);
        gson = new Gson();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    // TODO: 3/17/22 rewrite method to mockMvc standards
    @Test
    void findAllUsers_shouldReturnResponseEntityOfFoundUsers_ifUsersExist() throws Exception {
        // Given
        List<ApplicationUserDTO> userList = Arrays.asList(
                new ApplicationUserDTO(1000L, null, null, null, null, null),
                new ApplicationUserDTO(1001L, null, null, null, null, null),
                new ApplicationUserDTO(1002L, null, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<ApplicationUserDTO> resultPage = new PageImpl<>(userList, pageable, userList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<ApplicationUserDTO>> requestResult = controller.findAllUsers(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findUserByUsername_shouldReturnResponseEntityOfFoundUsers_ifUsersExist() throws Exception {
        // Given
        when(service.findByUsername(user.getUsername())).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.findUserByUsername(user.getUsername());
        // Then
        verify(service).findByUsername(user.getUsername());
        mockMvc.perform(get("/user/username/{username}", user.getUsername()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(requestResult.getBody())));
    }

    @Test
    void findUserByUsername_shouldReturnApiRequestExceptionAndStatusCode400_ifUserDoesNotExist() throws Exception {
        // Given
        when(service.findByUsername(user.getUsername())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/user/username/{username}", user.getUsername()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundUser_ifUserExists() throws Exception {
        // Given
        when(service.findById(user.getUserId())).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.findItemById(user.getUserId());
        // Then
        verify(service).findById(user.getUserId());
        mockMvc.perform(get("/user/{id}", user.getUserId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(requestResult.getBody())));
    }

    @Test
    void findItemById_shouldReturnApiRequestExceptionAndStatusCode400_ifUserDoesNotExist() throws Exception {
        // Given
        when(service.findById(user.getUserId())).thenThrow(new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
        // When
        // Then
        mockMvc.perform(get("/user/{id}", user.getUserId()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedUser_ifCreationWasSuccessful() throws Exception {
        // Given
        when(service.createItem(user)).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.createItem(user);
        // Then
        verify(service).createItem(user);
        mockMvc.perform(post("/user")
                        .content(gson.toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(gson.toJson(requestResult.getBody())));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedUser_ifUpdatingWasSuccessful() throws Exception {
        // Given
        when(service.updateItem(user)).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.updateItem(user);
        // Then
        verify(service).updateItem(user);
        mockMvc.perform(put("/user")
                        .content(gson.toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(gson.toJson(requestResult.getBody())));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedUser_ifDeletionWasSuccessful() throws Exception {
        // Given
        when(service.deleteItem(user.getUserId())).thenReturn(user);
        // When
        ResponseEntity<ApplicationUserDTO> requestResult = controller.deleteItem(user.getUserId());
        // Then
        verify(service).deleteItem(user.getUserId());
        mockMvc.perform(delete("/user/{id}", user.getUserId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(requestResult.getBody())));
    }
}