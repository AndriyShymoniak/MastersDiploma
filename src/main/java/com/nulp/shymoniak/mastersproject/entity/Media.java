package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "original_media_url")
    private String originalMediaUrl;

    @Column(name = "processed_media_url")
    private String processedMediaUrl;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "create_date")
    private LocalDateTime createDate;
}
