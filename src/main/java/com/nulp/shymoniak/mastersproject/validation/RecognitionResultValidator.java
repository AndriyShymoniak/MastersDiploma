package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO: 2/9/22 Add aspect on validation relations
//  AfterTrowing - return specified message in case of exception
@Component
public class RecognitionResultValidator implements Validator<RecognitionResultDTO> {
    private final ValidationUtility validationUtility;
    private final MLModelValidator mlModelValidator;
    private final MediaValidator mediaValidator;
    private final LocationValidator locationValidator;

    @Autowired
    public RecognitionResultValidator(ValidationUtility validationUtility, MLModelValidator mlModelValidator, MediaValidator mediaValidator, LocationValidator locationValidator) {
        this.validationUtility = validationUtility;
        this.mlModelValidator = mlModelValidator;
        this.mediaValidator = mediaValidator;
        this.locationValidator = locationValidator;
    }

    @Override
    public boolean isValid(RecognitionResultDTO recognitionResultDTO) {
        return validationUtility.isNotNullAndNotBlank(recognitionResultDTO.getDescription())
                && mlModelValidator.isValid(recognitionResultDTO.getMlModel())
                && mediaValidator.isValid(recognitionResultDTO.getMedia())
                && locationValidator.isValid(recognitionResultDTO.getLocation());
    }
}
