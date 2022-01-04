package com.nulp.shymoniak.mastersproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecognitionResultDTO {
    private Long recognitionResultId;
    private String description;
    private Integer isObjectPresent;
    private Integer is_recognition_correct;
    private Long mlModelId;
    private Long mediaId;
    private Long locationId;
    private Long createUser;
    private LocalDateTime createDate;
    private Long updateUser;
    private LocalDateTime updateDate;
}
