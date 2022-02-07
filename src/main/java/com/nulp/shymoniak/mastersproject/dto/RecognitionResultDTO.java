package com.nulp.shymoniak.mastersproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: 2/7/22 Change isRecognitionCorrect to Enum 
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private UserDTO createUser;
    private UserDTO updateUser;
    private List<RecognitionResultObservedObjectDTO> observedObjectList = new ArrayList<>();
}
