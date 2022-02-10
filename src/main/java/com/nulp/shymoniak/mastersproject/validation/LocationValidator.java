package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationValidator implements Validator<LocationDTO> {
    private final ValidationUtility validationUtility;

    @Autowired
    public LocationValidator(ValidationUtility validationUtility) {
        this.validationUtility = validationUtility;
    }

    // TODO: 2/9/22 add validation on coordinates format
    @Override
    public boolean isValid(LocationDTO locationDTO) {
        return validationUtility.isNotNullAndNotBlank(locationDTO.getLatitude())
                && validationUtility.isNotNullAndNotBlank(locationDTO.getLongitude());
    }
}
