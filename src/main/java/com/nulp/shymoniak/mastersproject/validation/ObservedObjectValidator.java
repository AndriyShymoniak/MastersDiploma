package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObservedObjectValidator implements Validator<ObservedObjectDTO> {
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public ObservedObjectValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    @Override
    public boolean isValid(ObservedObjectDTO observedObjectDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(observedObjectDTO.getObjectName());
    }
}
