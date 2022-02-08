package com.nulp.shymoniak.mastersproject.utility.validator;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecognitionResultValidator implements Validator<RecognitionResultDTO> {
    private final GeneralValidator generalValidator;

    @Autowired
    public RecognitionResultValidator(GeneralValidator generalValidator) {
        this.generalValidator = generalValidator;
    }

    @Override
    public boolean isValid(RecognitionResultDTO recognitionResultDTO) {
        return !generalValidator.isNotNullAndNotBlank(recognitionResultDTO.getDescription())
                || recognitionResultDTO.getMlModel() == null
                || recognitionResultDTO.getMedia() == null
                || recognitionResultDTO.getLocation() == null;
    }
}
