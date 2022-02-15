package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationValidator implements Validator<LocationDTO> {
    private final ValidationUtility validationUtility;

    // TODO: 2/9/22 add validation on coordinates format
    @Override
    public boolean isValid(LocationDTO locationDTO) {
        return validationUtility.isNotNullAndNotBlank(locationDTO.getLatitude())
                && validationUtility.isNotNullAndNotBlank(locationDTO.getLongitude());
    }
}
