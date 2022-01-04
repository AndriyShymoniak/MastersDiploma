package com.nulp.shymoniak.mastersproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MediaDTO {
    private Long mediaId;
    private String originalMediaUrl;
    private String processedMediaUrl;
    private Long createUser;
    private LocalDateTime createDate;
}
