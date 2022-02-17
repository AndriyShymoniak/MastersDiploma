package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecognitionResultRepository extends AbstractRepository<RecognitionResult, Long> {
    List<RecognitionResult> findAllByCreateUserOrUpdateUser(User createUser, User updateUser);

    @Query("select res from RecognitionResult res")
    List<RecognitionResult> findAllForList();
}
