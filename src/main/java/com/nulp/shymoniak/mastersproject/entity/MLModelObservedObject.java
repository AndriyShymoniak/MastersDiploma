package com.nulp.shymoniak.mastersproject.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "mlModelObservedObjectId")
@Entity
@Table(name = "ml_model_observed_object")
public class MLModelObservedObject implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMOOSeqGenerator")
    @SequenceGenerator(name = "MMOOSeqGenerator", sequenceName = "ml_model_observed_object_sequence", allocationSize = 20)
    @Column(name = "ml_model_observed_object_id")
    private Long mlModelObservedObjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ml_model_id")
    private MLModel mlModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observed_object_id")
    private ObservedObject observedObject;

    @Override
    public Long getId() {
        return mlModelObservedObjectId;
    }

    @Override
    public boolean isNew() {
        return mlModelObservedObjectId == null;
    }
}
