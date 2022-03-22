package com.nulp.shymoniak.mastersproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// TODO: 2/7/22 Change isRecognitionCorrect to Enum 
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("observedObjectList")
public class RecognitionResultDTO {
    private Long recognitionResultId;
    private String description;
    private Integer isObjectPresent;
    private Integer isRecognitionCorrect;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private MLModelDTO mlModel;
    private MediaDTO media;
    private LocationDTO location;
    private ApplicationUserDTO createUser;
    private ApplicationUserDTO updateUser;
    private List<RecognitionResultObservedObjectDTO> observedObjectList;
}
