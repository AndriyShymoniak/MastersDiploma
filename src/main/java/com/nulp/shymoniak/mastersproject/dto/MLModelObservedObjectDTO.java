package com.nulp.shymoniak.mastersproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLModelObservedObjectDTO {
    private Long mlModelObservedObjectId;

    private MLModelDTO mlModel;
    private ObservedObjectDTO observedObject;
}
