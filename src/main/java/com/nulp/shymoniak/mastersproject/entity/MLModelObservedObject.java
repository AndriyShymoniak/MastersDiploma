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
@Table(name = "ml_model_observed_object")
public class MLModelObservedObject implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ml_model_observed_object_id")
    private Long mlModelObservedObjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ml_model_id", referencedColumnName = "ml_model_id")
    @ToString.Exclude
    private MLModel mlModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observed_object_id", referencedColumnName = "observed_object_id")
    @ToString.Exclude
    private ObservedObject observedObject;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MLModelObservedObject that = (MLModelObservedObject) o;
        return mlModelObservedObjectId != null && Objects.equals(mlModelObservedObjectId, that.mlModelObservedObjectId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return mlModelObservedObjectId;
    }

    @Override
    public boolean isNew() {
        return mlModelObservedObjectId == null;
    }
}
