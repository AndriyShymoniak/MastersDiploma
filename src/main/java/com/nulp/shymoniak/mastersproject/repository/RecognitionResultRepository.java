package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecognitionResultRepository extends JpaRepository<RecognitionResult, Long> {
}
