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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ml_model_id", referencedColumnName = "ml_model_id")
    private MLModel mlModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observed_object_id", referencedColumnName = "observed_object_id")
    private ObservedObject observedObject;
}
