package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AbstractRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
