package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.utility.GeneralValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MediaValidator implements Validator<MediaDTO> {
    private final GeneralValidationUtility generalValidationUtility;

    @Autowired
    public MediaValidator(GeneralValidationUtility generalValidationUtility) {
        this.generalValidationUtility = generalValidationUtility;
    }

    @Override
    public boolean isValid(MediaDTO mediaDTO) {
        return generalValidationUtility.isNotNullAndNotBlank(mediaDTO.getOriginalMediaUrl())
                && generalValidationUtility.isValidURL(mediaDTO.getOriginalMediaUrl())
                && generalValidationUtility.isNotNullAndNotBlank(mediaDTO.getProcessedMediaUrl())
                && generalValidationUtility.isValidURL(mediaDTO.getProcessedMediaUrl());
    }
}
