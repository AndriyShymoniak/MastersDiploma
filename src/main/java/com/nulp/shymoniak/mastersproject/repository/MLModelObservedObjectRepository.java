package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.MLModelObservedObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MLModelObservedObjectRepository extends AbstractRepository<MLModelObservedObject, Long> {
}
