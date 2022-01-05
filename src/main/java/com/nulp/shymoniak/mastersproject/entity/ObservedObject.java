package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "observed_object")
public class ObservedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "observed_object_id")
    private Long observedObjectId;

    @Column(name = "object_name")
    private String objectName;

    @OneToMany(mappedBy = "mlModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MLModelObservedObject> mlModelList = new ArrayList<>();

    @OneToMany(mappedBy = "recognitionResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecognitionResultObservedObject> recognitionResultList = new ArrayList<>();
}
