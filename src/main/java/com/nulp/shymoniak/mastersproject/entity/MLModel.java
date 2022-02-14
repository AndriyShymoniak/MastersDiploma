package com.nulp.shymoniak.mastersproject.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ml_model")
public class MLModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ml_model_id")
    private Long mlModelId;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "model_path")
    private String modelPath;

    @Column(name = "is_custom")
    private Integer isCustom;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "create_user", referencedColumnName = "user_id")
    @ToString.Exclude
    private User createUser;

    @OneToMany(mappedBy = "observedObject", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<MLModelObservedObject> observedObjectList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MLModel mlModel = (MLModel) o;
        return mlModelId != null && Objects.equals(mlModelId, mlModel.mlModelId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
