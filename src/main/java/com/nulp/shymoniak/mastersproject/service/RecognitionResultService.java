package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;

import java.util.List;

public interface RecognitionResultService {
    List<RecognitionResultDTO> findAllRecognitionResults();

    List<RecognitionResultDTO> findAllByUserId(Long userId);

    RecognitionResultDTO findById(Long recognitionResultId);

    RecognitionResultDTO createRecognitionResult(RecognitionResultDTO recognitionResult);

    RecognitionResultDTO deleteRecognitionResult(Long recognitionResultId);

    RecognitionResultDTO updateRecognitionResult(RecognitionResultDTO recognitionResult);
}
