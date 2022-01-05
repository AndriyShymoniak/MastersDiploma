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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recognition_result_id", referencedColumnName = "recognition_result_id")
    private RecognitionResult recognitionResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observed_object_id", referencedColumnName = "observed_object_id")
    private ObservedObject observedObject;
}
