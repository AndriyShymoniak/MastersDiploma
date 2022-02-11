package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.utility.validation.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObservedObjectValidator implements Validator<ObservedObjectDTO> {
    private final ValidationUtility validationUtility;

    @Override
    public boolean isValid(ObservedObjectDTO observedObjectDTO) {
        return validationUtility.isValidStringWithCapitalLettersAndUnderscores(observedObjectDTO.getObjectName());
    }
}
