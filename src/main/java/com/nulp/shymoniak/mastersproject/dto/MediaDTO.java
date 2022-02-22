package com.nulp.shymoniak.mastersproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    private Long mediaId;
    private String originalMediaUrl;
    private String processedMediaUrl;
    private LocalDateTime createDate;

    private ApplicationUserDTO createUser;
}
