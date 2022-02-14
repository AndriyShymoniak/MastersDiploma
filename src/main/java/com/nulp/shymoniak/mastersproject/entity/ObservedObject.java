package com.nulp.shymoniak.mastersproject.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @ToString.Exclude
    private List<MLModelObservedObject> mlModelList = new ArrayList<>();

    @OneToMany(mappedBy = "recognitionResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
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
}
