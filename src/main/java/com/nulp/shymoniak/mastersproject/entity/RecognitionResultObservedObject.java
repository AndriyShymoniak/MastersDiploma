package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "recognition_result")
public class RecognitionResultObservedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recognition_result_observed_object_id")
    private Long recognitionResultObservedObjectId;

    @Column(name = "recognition_result_id")
    private Long recognitionResultId;

    @Column(name = "observed_object_id")
    private Long observedObjectId;
}
