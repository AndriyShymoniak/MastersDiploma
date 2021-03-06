package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MediaValidatorTest {
    private final MediaValidator mediaValidator;
    private static String validUrl;

    @Autowired
    public MediaValidatorTest(MediaValidator mediaValidator) {
        this.mediaValidator = mediaValidator;
    }

    @BeforeAll
    static void setUp() {
        validUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Fnature&psig=AOvVaw3NZIv4JgjEauWNhPiBjOAx&ust=1644499456633000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCNDF64Lc8vUCFQAAAAAdAAAAABAD";
    }

    @Test
    void validateMediaURL() {
        // Given
        MediaDTO invalidMedia1 = new MediaDTO(999L, null, validUrl, LocalDateTime.now(), new ApplicationUserDTO());
        MediaDTO invalidMedia2 = new MediaDTO(999L, "", validUrl, LocalDateTime.now(), new ApplicationUserDTO());
        MediaDTO invalidMedia3 = new MediaDTO(999L, "random word", validUrl, LocalDateTime.now(), new ApplicationUserDTO());
        MediaDTO invalidMedia4 = new MediaDTO(999L, validUrl, null, LocalDateTime.now(), new ApplicationUserDTO());
        MediaDTO invalidMedia5 = new MediaDTO(999L, validUrl, "", LocalDateTime.now(), new ApplicationUserDTO());
        MediaDTO invalidMedia6 = new MediaDTO(999L, validUrl, "random word", LocalDateTime.now(), new ApplicationUserDTO());
        MediaDTO validMedia1 = new MediaDTO(999L, validUrl, validUrl, LocalDateTime.now(), new ApplicationUserDTO());
        // Then
        assertFalse(mediaValidator.isValid(invalidMedia1));
        assertFalse(mediaValidator.isValid(invalidMedia2));
        assertFalse(mediaValidator.isValid(invalidMedia3));
        assertFalse(mediaValidator.isValid(invalidMedia4));
        assertFalse(mediaValidator.isValid(invalidMedia5));
        assertFalse(mediaValidator.isValid(invalidMedia6));
        assertTrue(mediaValidator.isValid(validMedia1));
    }
}