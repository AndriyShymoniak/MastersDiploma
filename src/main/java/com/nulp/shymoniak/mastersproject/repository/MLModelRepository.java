package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.MLModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MLModelRepository extends AbstractRepository<MLModel, Long> {
    @Query("select m from MLModel m where m.isActive = 1")
    List<MLModel> findAllActiveModels();
}
