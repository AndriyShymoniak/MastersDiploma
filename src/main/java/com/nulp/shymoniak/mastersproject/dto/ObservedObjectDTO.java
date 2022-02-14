package com.nulp.shymoniak.mastersproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"mlModelList", "recognitionResultList"})
public class ObservedObjectDTO {
    private Long observedObjectId;
    private String objectName;

    private List<MLModelObservedObjectDTO> mlModelList;
    private List<RecognitionResultObservedObjectDTO> recognitionResultList;
}
