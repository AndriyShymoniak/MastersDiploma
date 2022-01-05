package com.nulp.shymoniak.mastersproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLModelDTO {
    private Long mlModelId;
    private String modelName;
    private String modelPath;
    private Integer isCustom;
    private Integer isActive;
    private LocalDateTime createDate;

    private UserDTO createUser;
    private List<MLModelObservedObjectDTO> observedObjectList;
}
