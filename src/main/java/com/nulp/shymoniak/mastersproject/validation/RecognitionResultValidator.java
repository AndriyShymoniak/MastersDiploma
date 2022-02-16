package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// TODO: 2/9/22 Add aspect on validation relations
@Component
@RequiredArgsConstructor
public class RecognitionResultValidator implements Validator<RecognitionResultDTO> {
    private final ValidationUtility validationUtility;

    @Override
    public boolean isValid(RecognitionResultDTO recognitionResultDTO) {
        return validationUtility.isNotNullAndNotBlank(recognitionResultDTO.getDescription())
                && validationUtility.isValidBooleanRepresentedWithInteger(recognitionResultDTO.getIsObjectPresent())
                && validationUtility.isValidBooleanRepresentedWithInteger(recognitionResultDTO.getIsRecognitionCorrect());
    }
}
