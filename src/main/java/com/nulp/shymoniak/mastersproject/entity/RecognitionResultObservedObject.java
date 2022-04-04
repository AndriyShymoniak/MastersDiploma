package com.nulp.shymoniak.mastersproject.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recognition_result_observed_object")
public class RecognitionResultObservedObject implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RROOSeqGenerator")
    @SequenceGenerator(name = "RROOSeqGenerator", sequenceName = "recognition_result_observed_object_sequence", allocationSize = 20)
    @Column(name = "recognition_result_observed_object_id")
    private Long recognitionResultObservedObjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recognition_result_id", referencedColumnName = "recognition_result_id")
    @ToString.Exclude
    private RecognitionResult recognitionResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observed_object_id", referencedColumnName = "observed_object_id")
    @ToString.Exclude
    private ObservedObject observedObject;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecognitionResultObservedObject that = (RecognitionResultObservedObject) o;
        return recognitionResultObservedObjectId != null && Objects.equals(recognitionResultObservedObjectId, that.recognitionResultObservedObjectId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return recognitionResultObservedObjectId;
    }

    @Override
    public boolean isNew() {
        return recognitionResultObservedObjectId == null;
    }
}
