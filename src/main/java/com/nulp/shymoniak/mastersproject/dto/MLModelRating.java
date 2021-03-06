package com.nulp.shymoniak.mastersproject.dto;

import com.nulp.shymoniak.mastersproject.entity.enums.MLFeedback;
import lombok.Data;

import java.util.Map;

// TODO: 2/8/22 add rating logic
@Data
public class MLModelRating {
    private MLModelDTO mlModel;
    private Map<MLFeedback, Long> ratingMap;
    private Long usages;
}
