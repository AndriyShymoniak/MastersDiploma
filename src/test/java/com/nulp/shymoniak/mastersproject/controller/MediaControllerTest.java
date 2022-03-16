package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.service.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MediaControllerTest {
    @Mock
    private MediaService service;

    @InjectMocks
    private MediaController controller;

    private MediaDTO media;

    @BeforeEach
    void setUp() {
        controller = null;
        media = new MediaDTO(999L, "https://shorturl.at/elnI5", "https://shorturl.at/elnI5", null, null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllMedias_shouldReturnResponseEntityOfFoundMedias_ifMediasExist() {
        // Given
        List<MediaDTO> mediaList = Arrays.asList(
                new MediaDTO(1000L, null, null, null, null),
                new MediaDTO(1001L, null, null, null, null),
                new MediaDTO(1002L, null, null, null, null));
        Pageable pageable = PageRequest.of(0, 10);
        Page<MediaDTO> resultPage = new PageImpl<>(mediaList, pageable, mediaList.size());
        when(service.findAll(pageable)).thenReturn(resultPage);
        // When
        ResponseEntity<Page<MediaDTO>> requestResult = controller.findAllMedias(pageable);
        // Then
        verify(service).findAll(pageable);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(resultPage));
    }

    @Test
    void findItemById_shouldReturnResponseEntityOfFoundMedia_ifMediaExists() {
        // Given
        when(service.findById(media.getMediaId())).thenReturn(media);
        // When
        ResponseEntity<MediaDTO> requestResult = controller.findItemById(media.getMediaId());
        // Then
        verify(service).findById(media.getMediaId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(media));
    }

    @Test
    void createItem_shouldReturnResponseEntityOfCreatedMedia_ifCreationWasSuccessful() {
        // Given
        when(service.createItem(media)).thenReturn(media);
        // When
        ResponseEntity<MediaDTO> requestResult = controller.createItem(media);
        // Then
        verify(service).createItem(media);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(media));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedMedia_ifUpdatingWasSuccessful() {
        // Given
        when(service.updateItem(media)).thenReturn(media);
        // When
        ResponseEntity<MediaDTO> requestResult = controller.updateItem(media);
        // Then
        verify(service).updateItem(media);
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.CREATED));
        assertTrue(requestResult.getBody().equals(media));
    }

    @Test
    void updateItem_shouldReturnResponseEntityOfUpdatedMedia_ifDeletionWasSuccessful() {
        // Given
        when(service.deleteItem(media.getMediaId())).thenReturn(media);
        // When
        ResponseEntity<MediaDTO> requestResult = controller.deleteItem(media.getMediaId());
        // Then
        verify(service).deleteItem(media.getMediaId());
        assertTrue(requestResult.getStatusCode().equals(HttpStatus.OK));
        assertTrue(requestResult.getBody().equals(media));
    }
}