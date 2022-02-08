package com.nulp.shymoniak.mastersproject.utility.validator;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MLModelValidator implements Validator<MLModelDTO> {

    private final GeneralValidator generalValidator;

    @Autowired
    public MLModelValidator(GeneralValidator generalValidator) {
        this.generalValidator = generalValidator;
    }

    @Override
    public boolean isValid(MLModelDTO mlModelDTO) {
        return !generalValidator.isNotNullAndNotBlank(mlModelDTO.getModelName())
                || !generalValidator.isValidURL(mlModelDTO.getModelPath())
                || mlModelDTO.getObservedObjectList().size() <= 0;
    }
}
