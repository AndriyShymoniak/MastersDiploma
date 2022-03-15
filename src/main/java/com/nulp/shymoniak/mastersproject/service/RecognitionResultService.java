package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RecognitionResultService extends AbstractCrudService<RecognitionResultDTO> {

    List<RecognitionResultDTO> findAllByUserId(Long userId);

    Map<LocalDateTime, List<RecognitionResultDTO>> findAllGroupedByDate();

}
