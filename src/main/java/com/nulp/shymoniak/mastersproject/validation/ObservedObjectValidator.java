package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObservedObjectValidator implements Validator<ObservedObjectDTO> {
    private final ValidationUtility validationUtility;

    // TODO: 2/9/22 add validation fields
    @Override
    public boolean isValid(ObservedObjectDTO observedObjectDTO) {
        return validationUtility.isNotNullAndNotBlank(observedObjectDTO.getObjectName());
    }
}
