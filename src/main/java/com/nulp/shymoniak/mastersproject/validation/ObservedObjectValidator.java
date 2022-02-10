package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObservedObjectValidator implements Validator<ObservedObjectDTO> {
    private final ValidationUtility validationUtility;

    @Autowired
    public ObservedObjectValidator(ValidationUtility validationUtility) {
        this.validationUtility = validationUtility;
    }

    // TODO: 2/9/22 add validation fields
    @Override
    public boolean isValid(ObservedObjectDTO observedObjectDTO) {
        return validationUtility.isNotNullAndNotBlank(observedObjectDTO.getObjectName());
    }
}
