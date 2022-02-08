package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.utility.ObjectMapperUtils;
import com.nulp.shymoniak.mastersproject.utility.validator.RecognitionResultValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecognitionResultServiceImpl implements RecognitionResultService {
    private final RecognitionResultRepository recognitionResultRepository;
    private final ObjectMapperUtils mapper;
    private final RecognitionResultValidator validator;

    @Autowired
    public RecognitionResultServiceImpl(RecognitionResultRepository recognitionResultRepository, ObjectMapperUtils mapper, RecognitionResultValidator validator) {
        this.recognitionResultRepository = recognitionResultRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<RecognitionResultDTO> findAllRecognitionResults() {
        List<RecognitionResult> recognitionResultList = recognitionResultRepository.findAll();
        return mapper.mapAll(recognitionResultList, RecognitionResultDTO.class);
    }

    @Override
    public List<RecognitionResultDTO> findAllByUserId(Long userId) {
        List<RecognitionResult> resultsByUser = recognitionResultRepository.findAllByCreateUserOrUpdateUser(userId, userId);
        return mapper.mapAll(resultsByUser, RecognitionResultDTO.class);
    }

    @Override
    public Map<LocalDateTime, List<RecognitionResultDTO>> findAllGroupedByDate() {
        List<RecognitionResult> recognitionResultList = recognitionResultRepository.findAll();
        List<RecognitionResultDTO> recognitionResultDTOList = mapper.mapAll(recognitionResultList, RecognitionResultDTO.class);
        Map<LocalDateTime, List<RecognitionResultDTO>> result = recognitionResultDTOList.stream()
                .collect(Collectors.groupingBy(item -> item.getCreateDate().truncatedTo(ChronoUnit.DAYS)));
        return new TreeMap<>(result);
    }

    @Override
    public RecognitionResultDTO findById(Long recognitionResultId) {
        Optional<RecognitionResult> optionalUser = recognitionResultRepository.findById(recognitionResultId);
        return optionalUser.map(item -> mapper.map(item, RecognitionResultDTO.class))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    // TODO: 2/7/22 fill createUser, createDate
    @Transactional
    @Override
    public RecognitionResultDTO createRecognitionResult(RecognitionResultDTO recognitionResult) {
        checkIfValid(recognitionResult);
        RecognitionResult recognitionResultEntity = mapper.map(recognitionResult, RecognitionResult.class);
        recognitionResultRepository.save(recognitionResultEntity);
        return mapper.map(recognitionResultEntity, RecognitionResultDTO.class);
    }

    // TODO: 2/7/22 fill updateDate, updateUser
    @Transactional
    @Override
    public RecognitionResultDTO deleteRecognitionResult(Long recognitionResultId) {
        RecognitionResult recognitionResultEntity = recognitionResultRepository.findById(recognitionResultId)
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        recognitionResultRepository.delete(recognitionResultEntity);
        return mapper.map(recognitionResultEntity, RecognitionResultDTO.class);
    }

    // TODO: 2/7/22 fill updateDate, updateUser
    @Transactional
    @Override
    public RecognitionResultDTO updateRecognitionResult(RecognitionResultDTO recognitionResult) {
        checkIfValid(recognitionResult);
        RecognitionResult recognitionResultEntity = recognitionResultRepository.findById(recognitionResult.getRecognitionResultId())
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        recognitionResultRepository.save(recognitionResultEntity);
        return recognitionResult;
    }

    // TODO: 2/8/22  Create AbstractService class with validation through Generics
    // TODO: 2/8/22 Validate using AOP
    private void checkIfValid(RecognitionResultDTO recognitionResult){
        if (validator.isValid(recognitionResult)) {
            throw new ApiRequestException(ApplicationConstants.ERROR_INVALID_ENTITY + recognitionResult);
        }
    }
}