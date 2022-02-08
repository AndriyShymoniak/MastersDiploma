package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecognitionResultValidator implements Validator<RecognitionResultDTO> {
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public RecognitionResultValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    @Override
    public boolean isValid(RecognitionResultDTO recognitionResultDTO) {
        return !generalValidationUtility.isNotNullAndNotBlank(recognitionResultDTO.getDescription())
                || recognitionResultDTO.getMlModel() == null
                || recognitionResultDTO.getMedia() == null
                || recognitionResultDTO.getLocation() == null;
    }
}
