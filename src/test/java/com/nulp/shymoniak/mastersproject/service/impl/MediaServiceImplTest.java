package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.TestObjectsGenerator;
import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.entity.Media;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.MediaMapper;
import com.nulp.shymoniak.mastersproject.repository.MediaRepository;
import org.junit.jupiter.api.BeforeAll;
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
class MediaServiceImplTest {
    @Mock
    private MediaRepository repository;

    @Mock
    private MediaMapper mapper;

    @InjectMocks
    private MediaServiceImpl service;

    private static Media media;
    private static MediaDTO mediaDTO;

    @BeforeAll
    static void beforeAll() {
        media = TestObjectsGenerator.generateMedia();
        mediaDTO = TestObjectsGenerator.generateMediaDTO();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnMedia_IfFinds() {
        // Given
        when(repository.findById(media.getMediaId())).thenReturn(Optional.of(media));
        when(mapper.mapToDTO(media)).thenReturn(mediaDTO);
        // When
        MediaDTO result = service.findById(media.getMediaId());
        // Then
        verify(repository).findById(media.getMediaId());
        verify(mapper).mapToDTO(media);
        assertEquals(mediaDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfCanNotFind() {
        // Given
        when(repository.findById(media.getMediaId())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(ApiRequestException.class,
                () -> service.findById(media.getMediaId()));
        verify(repository).findById(media.getMediaId());
    }

    @Test
    void createItem_shouldSaveMedia() {
        // Given
        when(mapper.mapToEntity(mediaDTO)).thenReturn(media);
        when(mapper.mapToDTO(media)).thenReturn(mediaDTO);
        when(repository.save(media)).thenReturn(media);
        // When
        MediaDTO result = service.createItem(mediaDTO);
        // Then
        verify(mapper).mapToEntity(mediaDTO);
        verify(mapper).mapToDTO(media);
        verify(repository).save(media);
        assertEquals(mediaDTO, result);
    }

    @Test
    void updateItem_shouldUpdateMedia_ifExist() {
        // Given
        Media newMediaEntity = TestObjectsGenerator.generateMedia();
        newMediaEntity.setProcessedMediaUrl("https://newlink.com/");
        MediaDTO newMediaDTO = TestObjectsGenerator.generateMediaDTO();
        newMediaDTO.setProcessedMediaUrl("https://newlink.com/");
        when(mapper.mapToEntity(newMediaDTO)).thenReturn(newMediaEntity);
        when(mapper.mapToDTO(newMediaEntity)).thenReturn(newMediaDTO);
        when(repository.existsById(media.getMediaId())).thenReturn(true);
        when(repository.save(newMediaEntity)).thenReturn(newMediaEntity);
        // When
        MediaDTO result = service.updateItem(newMediaDTO);
        // Then
        verify(mapper).mapToEntity(newMediaDTO);
        verify(repository).existsById(media.getMediaId());
        verify(repository).save(newMediaEntity);
        assertEquals(newMediaDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // Given
        when(mapper.mapToEntity(mediaDTO)).thenReturn(media);
        when(repository.existsById(media.getMediaId())).thenReturn(false);
        // When
        // Then
        assertThrows(ApiRequestException.class,
                () -> service.updateItem(mediaDTO));
        verify(mapper).mapToEntity(mediaDTO);
        verify(repository).existsById(media.getMediaId());
    }

    @Test
    void deleteItem_shouldDeleteMedia_IfFinds() {
        // Given
        when(repository.findById(media.getMediaId())).thenReturn(Optional.of(media));
        when(mapper.mapToDTO(media)).thenReturn(mediaDTO);
        // When
        MediaDTO result = service.deleteItem(media.getMediaId());
        // Then
        verify(repository).findById(media.getMediaId());
        verify(repository).deleteById(media.getMediaId());
        assertEquals(mediaDTO, result);
    }

}