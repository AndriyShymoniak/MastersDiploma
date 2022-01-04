package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recognition_result")
public class RecognitionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recognition_result_id")
    private Long recognitionResultId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_object_present")
    private Integer isObjectPresent;

    @Column(name = "is_recognition_correct")
    private Integer is_recognition_correct;

    @Column(name = "ml_model_id")
    private Long mlModelId;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
