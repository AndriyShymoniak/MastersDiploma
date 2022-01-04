package com.nulp.shymoniak.mastersproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MLModelDTO {
    private Long mlModelId;
    private String modelName;
    private String modelPath;
    private Integer isCustom;
    private Integer isActive;
    private Long createUser;
    private LocalDateTime createDate;
}
