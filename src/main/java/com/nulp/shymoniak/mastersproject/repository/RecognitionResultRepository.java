package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecognitionResultRepository extends AbstractRepository<RecognitionResult, Long> {
    List<RecognitionResult> findAllByCreateUserOrUpdateUser(ApplicationUser createUser, ApplicationUser updateUser);

    @Query("select res from RecognitionResult res")
    List<RecognitionResult> findAllForList();
}
