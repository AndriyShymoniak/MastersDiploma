package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;

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
}
