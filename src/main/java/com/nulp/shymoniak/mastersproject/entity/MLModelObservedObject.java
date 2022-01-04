package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "media")
public class MLModelObservedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ml_model_observed_object_id")
    private Long mlModelObservedObjectId;

    @Column(name = "ml_model_id")
    private Long mlModelId;

    @Column(name = "observed_object_id")
    private Long observedObjectId;
}
