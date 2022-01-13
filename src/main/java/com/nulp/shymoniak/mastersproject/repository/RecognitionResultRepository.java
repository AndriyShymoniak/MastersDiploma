package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecognitionResultRepository extends JpaRepository<RecognitionResult, Long> {
    List<RecognitionResult> findAllByCreateUserOrUpdateUser(Long createUserId, Long updateUserId);
    List<RecognitionResult> findAllBy(Long createUserId, Long updateUserId);

}
