package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MediaValidator implements Validator<MediaDTO> {
    private final ValidationUtility validationUtility;

    @Override
    public boolean isValid(MediaDTO mediaDTO) {
        return validationUtility.isValidURL(mediaDTO.getOriginalMediaUrl())
                && validationUtility.isValidURL(mediaDTO.getProcessedMediaUrl());
    }
}
