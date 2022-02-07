package com.nulp.shymoniak.mastersproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Integer isRecognitionCorrect;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ml_model_id", referencedColumnName = "ml_model_id")
    private MLModel mlModel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", referencedColumnName = "media_id")
    private Media media;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "create_user", referencedColumnName = "user_id")
    private User createUser;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "update_user", referencedColumnName = "user_id")
    private User updateUser;

    @OneToMany(mappedBy = "observedObject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecognitionResultObservedObject> observedObjectList = new ArrayList<>();
}
