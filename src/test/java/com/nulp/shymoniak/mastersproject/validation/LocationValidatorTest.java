package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationValidatorTest {
    private final LocationValidator locationValidator;

    @Autowired
    public LocationValidatorTest(LocationValidator locationValidator) {
        this.locationValidator = locationValidator;
    }

    @Test
    public void validateLongitudeAndLatitude(){
        // given
        LocationDTO invalidLocation1 = new LocationDTO(999L, null, "39.847446");
        LocationDTO invalidLocation2 = new LocationDTO(999L, "", "39.847446");
        LocationDTO invalidLocation3 = new LocationDTO(999L, "39.847446", null);
        LocationDTO invalidLocation4 = new LocationDTO(999L, "39.847446", "");
        LocationDTO validLocation1 = new LocationDTO(999L, "39.847446", "39.847446");
        // then
        assertFalse(locationValidator.isValid(invalidLocation1));
        assertFalse(locationValidator.isValid(invalidLocation2));
        assertFalse(locationValidator.isValid(invalidLocation3));
        assertFalse(locationValidator.isValid(invalidLocation4));
        assertTrue(locationValidator.isValid(validLocation1));
    }
}