package com.nulp.shymoniak.mastersproject.entity.enums;

import lombok.Getter;

@Getter
public enum MLFeedback {
    CORRECT("Correct", 1),
    PARTIALLY_CORRECT("Partially correct", 2),
    INCORRECT("Incorrect", 3);

    private final Integer id;
    private final String feedback;

    MLFeedback(String feedback, Integer id) {
        this.id = id;
        this.feedback = feedback;
    }
}
