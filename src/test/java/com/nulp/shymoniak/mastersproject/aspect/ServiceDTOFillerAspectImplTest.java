package com.nulp.shymoniak.mastersproject.aspect;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.entity.Media;
import com.nulp.shymoniak.mastersproject.mapping.MediaMapper;
import com.nulp.shymoniak.mastersproject.repository.MediaRepository;
import com.nulp.shymoniak.mastersproject.service.MediaService;
import com.nulp.shymoniak.mastersproject.service.impl.MediaServiceImpl;
import com.nulp.shymoniak.mastersproject.utility.AspectUtility;
import com.nulp.shymoniak.mastersproject.utility.DTOFieldFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ServiceDTOFillerAspectImplTest {
    @Mock
    private MediaRepository repository;

    @Mock
    private MediaMapper mapper;

    @Mock
    private AspectUtility aspectUtility;

    @Mock
    private DTOFieldFiller fieldFiller;

    @InjectMocks
    private MediaServiceImpl mediaServiceImpl;

    @InjectMocks
    private ServiceDTOFillerAspectImpl aspectForService;

    private Media updatedMediaEntity;
    private MediaDTO mediaDTO;
    private MediaDTO updatedMediaDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime currentDate = LocalDateTime.now();
        mediaDTO = new MediaDTO(999L, "https://shorturl.at/elnI5", "https://shorturl.at/elnI5", null, null);
        updatedMediaEntity = new Media(999L, "https://shorturl.at/elnI5", "https://shorturl.at/elnI5", currentDate, new ApplicationUser());
        updatedMediaDTO = new MediaDTO(999L, "https://shorturl.at/elnI5", "https://shorturl.at/elnI5", currentDate, new ApplicationUserDTO());
        aspectForService = null;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fillDTOFieldsOnCreate_shouldFillCreateFields_whenCalled() {
        // given
        when(aspectUtility.getDTOEntityFromParameters(any())).thenReturn(mediaDTO);
        when(fieldFiller.fillCreateFields(mediaDTO)).thenReturn(updatedMediaDTO);
        when(mapper.mapToEntity(updatedMediaDTO)).thenReturn(updatedMediaEntity);
        when(mapper.mapToDTO(updatedMediaEntity)).thenReturn(updatedMediaDTO);
        AspectJProxyFactory factory = new AspectJProxyFactory(mediaServiceImpl);
        factory.addAspect(aspectForService);
        MediaService serviceProxy = factory.getProxy();
        // when
        MediaDTO result = serviceProxy.createItem(mediaDTO);
        // then
        assertTrue(result.getCreateDate() != null);
        assertTrue(result.getCreateUser() != null);
    }
}