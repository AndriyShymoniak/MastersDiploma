package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.utility.validation.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// TODO: 2/11/22 update test class
@Component
@RequiredArgsConstructor
public class MLModelValidator implements Validator<MLModelDTO> {
    private final ValidationUtility validationUtility;

    @Override
    public boolean isValid(MLModelDTO mlModelDTO) {
        return validationUtility.isNotNullAndNotBlank(mlModelDTO.getModelName())
                && validationUtility.isValidURL(mlModelDTO.getModelPath())
                && validationUtility.isValidBooleanRepresentedWithInteger(mlModelDTO.getIsCustom())
                && validationUtility.isValidBooleanRepresentedWithInteger(mlModelDTO.getIsActive());
    }
}
