package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.MLModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MLModelRepository extends JpaRepository<MLModel, Long> {
}
