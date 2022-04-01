package com.nulp.shymoniak.mastersproject.entity;

import com.nulp.shymoniak.mastersproject.entity.enums.ApplicationUserRole;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application_user")
public class ApplicationUser implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "user_sequence", allocationSize = 20)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "registered_date")
    private LocalDateTime createDate;

    @OneToOne(mappedBy = "applicationUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Person person;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApplicationUser user = (ApplicationUser) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return userId == null;
    }
}
