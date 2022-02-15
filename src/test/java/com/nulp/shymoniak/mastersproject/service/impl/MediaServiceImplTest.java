package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.entity.Media;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.mapping.MediaMapper;
import com.nulp.shymoniak.mastersproject.repository.MediaRepository;
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

    private Media media;
    private MediaDTO mediaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        media = new Media(999L, "https://shorturl.at/elnI5", "https://shorturl.at/elnI5", null, null);
        mediaDTO = new MediaDTO(999L, "https://shorturl.at/elnI5", "https://shorturl.at/elnI5", null, null);
    }

    @Test
    void findById_shouldReturnMedia_IfFinds() {
        // given
        when(repository.findById(media.getMediaId())).thenReturn(Optional.of(media));
        when(mapper.mapToDTO(media)).thenReturn(mediaDTO);
        // when
        MediaDTO result = service.findById(media.getMediaId());
        // then
        verify(repository).findById(media.getMediaId());
        verify(mapper).mapToDTO(media);
        assertEquals(mediaDTO, result);
    }

    @Test
    void findById_shouldThrowException_IfNotFinds() {
        // given
        when(repository.findById(media.getMediaId())).thenReturn(Optional.empty());
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.findById(media.getMediaId()));
        verify(repository).findById(media.getMediaId());
    }

    @Test
    void createItem_shouldSaveMedia() {
        // given
        when(mapper.mapToEntity(mediaDTO)).thenReturn(media);
        when(mapper.mapToDTO(media)).thenReturn(mediaDTO);
        // when
        MediaDTO result = service.createItem(mediaDTO);
        // then
        verify(mapper).mapToEntity(mediaDTO);
        verify(mapper).mapToDTO(media);
        verify(repository).save(media);
        assertEquals(mediaDTO, result);
    }

    @Test
    void updateItem_shouldUpdateMedia_ifExist() {
        // given
        Media newMediaEntity = new Media(999L, "https://github.com/", "", null, null);
        MediaDTO newMediaDTO = new MediaDTO(999L, "https://github.com/", "", null, null);
        when(repository.existsById(media.getMediaId())).thenReturn(true);
        when(mapper.mapToEntity(newMediaDTO)).thenReturn(newMediaEntity);
        when(mapper.mapToDTO(newMediaEntity)).thenReturn(newMediaDTO);
        // when
        MediaDTO result = service.updateItem(newMediaDTO);
        // then
        verify(mapper).mapToEntity(newMediaDTO);
        verify(repository).existsById(media.getMediaId());
        verify(repository).save(newMediaEntity);
        assertEquals(newMediaDTO, result);
    }

    @Test
    void updateItem_shouldThrowException_ifDoesNotExist() {
        // given
        when(mapper.mapToEntity(mediaDTO)).thenReturn(media);
        when(repository.existsById(media.getMediaId())).thenReturn(false);
        // when
        // then
        assertThrows(ApiRequestException.class, () -> service.updateItem(mediaDTO));
        verify(mapper).mapToEntity(mediaDTO);
        verify(repository).existsById(media.getMediaId());
    }

    @Test
    void deleteItem_shouldDeleteMedia_IfFinds() {
        // given
        when(repository.findById(media.getMediaId())).thenReturn(Optional.of(media));
        when(mapper.mapToDTO(media)).thenReturn(mediaDTO);
        // when
        MediaDTO result = service.deleteItem(media.getMediaId());
        // then
        verify(repository).findById(media.getMediaId());
        verify(repository).deleteById(media.getMediaId());
        assertEquals(mediaDTO, result);
    }

}