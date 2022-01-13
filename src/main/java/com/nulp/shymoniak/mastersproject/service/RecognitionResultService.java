package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RecognitionResultService {
    List<RecognitionResultDTO> findAllRecognitionResults();

    List<RecognitionResultDTO> findAllByUserId(Long userId);

    Map<LocalDateTime, List<RecognitionResultDTO>> findAllGroupedByDate();

    RecognitionResultDTO findById(Long recognitionResultId);

    RecognitionResultDTO createRecognitionResult(RecognitionResultDTO recognitionResult);

    RecognitionResultDTO deleteRecognitionResult(Long recognitionResultId);

    RecognitionResultDTO updateRecognitionResult(RecognitionResultDTO recognitionResult);
}
