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
@Table(name = "location")
public class Location implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LocSeqGenerator")
    @SequenceGenerator(name = "LocSeqGenerator", sequenceName = "location_sequence", allocationSize = 20)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "longitude", nullable = false, length = 100)
    private String longitude;

    @Column(name = "latitude", nullable = false, length = 100)
    private String latitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Location location = (Location) o;
        return locationId != null && Objects.equals(locationId, location.locationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return locationId;
    }

    @Override
    public boolean isNew() {
        return locationId == null;
    }
}
