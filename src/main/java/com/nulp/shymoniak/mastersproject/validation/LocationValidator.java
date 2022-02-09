package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationValidator implements Validator<LocationDTO> {
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public LocationValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    // TODO: 2/9/22 add validation on coordinates format
    @Override
    public boolean isValid(LocationDTO locationDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(locationDTO.getLatitude())
                && generalValidationUtility.isNotNullAndNotBlank(locationDTO.getLongitude());
    }
}
