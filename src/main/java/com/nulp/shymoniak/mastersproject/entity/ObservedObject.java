package com.nulp.shymoniak.mastersproject.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "observed_object")
public class ObservedObject implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ObsObjSeqGenerator")
    @SequenceGenerator(name = "ObsObjSeqGenerator", sequenceName = "observed_object_sequence", allocationSize = 20)
    @Column(name = "observed_object_id")
    private Long observedObjectId;

    @Column(name = "object_name", nullable = false, length = 50)
    private String objectName;

    @OneToMany(mappedBy = "mlModel", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MLModelObservedObject> mlModelList = new ArrayList<>();

    @OneToMany(mappedBy = "recognitionResult", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RecognitionResultObservedObject> recognitionResultList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ObservedObject that = (ObservedObject) o;
        return observedObjectId != null && Objects.equals(observedObjectId, that.observedObjectId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return observedObjectId;
    }

    @Override
    public boolean isNew() {
        return observedObjectId == null;
    }
}
