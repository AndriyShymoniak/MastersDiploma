package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MLModelValidator implements Validator<MLModelDTO> {

    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public MLModelValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    @Override
    public boolean isValid(MLModelDTO mlModelDTO) {
        return !generalValidationUtility.isNotNullAndNotBlank(mlModelDTO.getModelName())
                || !generalValidationUtility.isValidURL(mlModelDTO.getModelPath())
                || mlModelDTO.getObservedObjectList().size() <= 0;
    }
}
