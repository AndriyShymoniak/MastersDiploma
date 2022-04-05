package com.nulp.shymoniak.mastersproject.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "recognitionResultObservedObjectId")
@Entity
@Table(name = "recognition_result_observed_object")
public class RecognitionResultObservedObject implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RROOSeqGenerator")
    @SequenceGenerator(name = "RROOSeqGenerator", sequenceName = "recognition_result_observed_object_sequence", allocationSize = 20)
    @Column(name = "recognition_result_observed_object_id")
    private Long recognitionResultObservedObjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recognition_result_id")
    private RecognitionResult recognitionResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observed_object_id")
    private ObservedObject observedObject;

    @Override
    public Long getId() {
        return recognitionResultObservedObjectId;
    }

    @Override
    public boolean isNew() {
        return recognitionResultObservedObjectId == null;
    }
}
