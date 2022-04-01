package com.nulp.shymoniak.mastersproject.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recognition_result")
public class RecognitionResult implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "recognition_result_sequence", allocationSize = 20)
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
    @ToString.Exclude
    private MLModel mlModel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", referencedColumnName = "media_id")
    @ToString.Exclude
    private Media media;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ToString.Exclude
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "create_user", referencedColumnName = "user_id")
    @ToString.Exclude
    private ApplicationUser createUser;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "update_user", referencedColumnName = "user_id")
    @ToString.Exclude
    private ApplicationUser updateUser;

    @OneToMany(mappedBy = "observedObject", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<RecognitionResultObservedObject> observedObjectList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecognitionResult that = (RecognitionResult) o;
        return recognitionResultId != null && Objects.equals(recognitionResultId, that.recognitionResultId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return recognitionResultId;
    }

    @Override
    public boolean isNew() {
        return recognitionResultId == null;
    }
}
