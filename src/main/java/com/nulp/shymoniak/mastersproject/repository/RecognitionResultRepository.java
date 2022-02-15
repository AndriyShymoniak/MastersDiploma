package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecognitionResultRepository extends AbstractRepository<RecognitionResult, Long> {
    // TODO: 2/8/22 Searching by user, not user id. Bug expected
    List<RecognitionResult> findAllByCreateUserOrUpdateUser(Long createUserId, Long updateUserId);
}
