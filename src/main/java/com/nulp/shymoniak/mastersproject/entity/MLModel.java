package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ml_model")
public class MLModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ml_model_id")
    private Long mlModelId;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "model_path")
    private String modelPath;

    @Column(name = "is_custom")
    private Integer isCustom;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "create_date")
    private LocalDateTime createDate;
}
