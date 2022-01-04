package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.utility.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecognitionResultServiceImpl implements RecognitionResultService {
    private final RecognitionResultRepository recognitionResultRepository;
    private final ObjectMapperUtils mapper;

    @Autowired
    public RecognitionResultServiceImpl(RecognitionResultRepository recognitionResultRepository, ObjectMapperUtils mapper) {
        this.recognitionResultRepository = recognitionResultRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RecognitionResultDTO> findAllRecognitionResults() {
        List<RecognitionResult> recognitionResultList = recognitionResultRepository.findAll();
        return mapper.mapAll(recognitionResultList, RecognitionResultDTO.class);
    }

    // TODO: 2022-01-04
    @Override
    public List<RecognitionResultDTO> findAllByUserId(Long userId) {
//        Optional<RecognitionResult> optionalRecognitionResult =
//                recognitionResultRepository.findById(userId);
//        return optionalRecognitionResult.map(item -> mapper.map(item, RecognitionResultDTO.class))
//                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        return null;
    }

    @Override
    public RecognitionResultDTO findById(Long recognitionResultId) {
        Optional<RecognitionResult> optionalUser = recognitionResultRepository.findById(recognitionResultId);
        return optionalUser.map(item -> mapper.map(item, RecognitionResultDTO.class))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Transactional
    @Override
    public RecognitionResultDTO createRecognitionResult(RecognitionResultDTO recognitionResult) {
        RecognitionResult recognitionResultEntity = mapper.map(recognitionResult, RecognitionResult.class);
        recognitionResultRepository.save(recognitionResultEntity);
        return mapper.map(recognitionResultEntity, RecognitionResultDTO.class);
    }

    @Transactional
    @Override
    public RecognitionResultDTO deleteRecognitionResult(Long recognitionResultId) {
        RecognitionResult recognitionResultEntity = recognitionResultRepository.findById(recognitionResultId)
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        recognitionResultRepository.delete(recognitionResultEntity);
        return mapper.map(recognitionResultEntity, RecognitionResultDTO.class);
    }

    @Transactional
    @Override
    public RecognitionResultDTO updateRecognitionResult(RecognitionResultDTO recognitionResult) {
        RecognitionResult recognitionResultEntity = recognitionResultRepository.findById(recognitionResult.getRecognitionResultId())
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        recognitionResultRepository.save(recognitionResultEntity);
        return recognitionResult;
    }
}