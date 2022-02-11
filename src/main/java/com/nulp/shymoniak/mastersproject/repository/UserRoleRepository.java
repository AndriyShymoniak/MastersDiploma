package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends AbstractRepository<UserRole, Long> {
}
