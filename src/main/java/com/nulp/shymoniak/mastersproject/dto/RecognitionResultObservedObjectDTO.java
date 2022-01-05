package com.nulp.shymoniak.mastersproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecognitionResultObservedObjectDTO {
    private Long recognitionResultObservedObjectId;

    private RecognitionResultDTO recognitionResult;
    private ObservedObjectDTO observedObject;
}
