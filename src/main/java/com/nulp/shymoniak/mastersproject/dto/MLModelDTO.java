package com.nulp.shymoniak.mastersproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("observedObjectList")
public class MLModelDTO {
    private Long mlModelId;
    private String modelName;
    private String modelPath;
    private Integer isCustom;
    private Integer isActive;
    private LocalDateTime createDate;

    private ApplicationUserDTO createUser;

    @ToString.Exclude
    private List<MLModelObservedObjectDTO> observedObjectList;
}
