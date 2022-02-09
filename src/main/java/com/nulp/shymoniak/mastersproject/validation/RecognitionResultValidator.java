package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO: 2/9/22 Add aspect on validation relations
//  AfterTrowing - return specified message in case of exception
@Component
public class RecognitionResultValidator implements Validator<RecognitionResultDTO> {
    private final GeneralValidationUtility generalValidationUtility;
    private final MLModelValidator mlModelValidator;
    private final MediaValidator mediaValidator;
    private final LocationValidator locationValidator;

    @Autowired
    public RecognitionResultValidator(GeneralValidationUtility generalValidationUtility, MLModelValidator mlModelValidator, MediaValidator mediaValidator, LocationValidator locationValidator) {
        this.generalValidationUtility = generalValidationUtility;
        this.mlModelValidator = mlModelValidator;
        this.mediaValidator = mediaValidator;
        this.locationValidator = locationValidator;
    }

    @Override
    public boolean isValid(RecognitionResultDTO recognitionResultDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(recognitionResultDTO.getDescription())
                || mlModelValidator.isValid(recognitionResultDTO.getMlModel())
                || mediaValidator.isValid(recognitionResultDTO.getMedia())
                || locationValidator.isValid(recognitionResultDTO.getLocation());
    }
}
