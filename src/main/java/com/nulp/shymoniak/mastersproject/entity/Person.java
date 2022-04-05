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
@Table(name = "person")
public class Person implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonSeqGenerator")
    @SequenceGenerator(name = "PersonSeqGenerator", sequenceName = "person_sequence", allocationSize = 20)
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "surname", length = 100)
    private String surname;

    @Column(name = "email", length = 100)
    private String email;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private ApplicationUser applicationUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return personId != null && Objects.equals(personId, person.personId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return personId;
    }

    @Override
    public boolean isNew() {
        return personId == null;
    }
}
