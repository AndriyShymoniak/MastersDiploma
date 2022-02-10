package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MediaValidator implements Validator<MediaDTO> {
    private final ValidationUtility validationUtility;

    @Autowired
    public MediaValidator(ValidationUtility validationUtility) {
        this.validationUtility = validationUtility;
    }

    @Override
    public boolean isValid(MediaDTO mediaDTO) {
        return validationUtility.isNotNullAndNotBlank(mediaDTO.getOriginalMediaUrl())
                && validationUtility.isValidURL(mediaDTO.getOriginalMediaUrl())
                && validationUtility.isNotNullAndNotBlank(mediaDTO.getProcessedMediaUrl())
                && validationUtility.isValidURL(mediaDTO.getProcessedMediaUrl());
    }
}
