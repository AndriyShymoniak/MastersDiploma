package com.nulp.shymoniak.mastersproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRole extends JpaRepository<UserRole, Long> {
}
