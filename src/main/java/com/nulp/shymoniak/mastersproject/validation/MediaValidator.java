package com.nulp.shymoniak.mastersproject.validation;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import org.springframework.stereotype.Component;

@Component
public class MediaValidator implements Validator<MediaDTO> {
    // TODO: 2/9/22 add validation on fields
    @Override
    public boolean isValid(MediaDTO mediaDTO) {
        return true;
    }
}
