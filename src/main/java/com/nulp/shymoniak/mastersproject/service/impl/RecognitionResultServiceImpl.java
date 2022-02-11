package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.utility.mapping.RecognitionResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecognitionResultServiceImpl extends AbstractService<RecognitionResultDTO> implements RecognitionResultService {
    private final RecognitionResultRepository recognitionResultRepository;
    private RecognitionResultMapper mapper = RecognitionResultMapper.INSTANCE;

    @Override
    public List<RecognitionResultDTO> findAll() {
        List<RecognitionResult> recognitionResultList = recognitionResultRepository.findAll();
        return mapper.map(recognitionResultList);
    }

    @Override
    public RecognitionResultDTO findById(Long id) {
        Optional<RecognitionResult> optionalUser = recognitionResultRepository.findById(id);
        return optionalUser.map(item -> mapper.map(item))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Override
    public RecognitionResultDTO createItem(RecognitionResultDTO recognitionResult) {
        RecognitionResult recognitionResultEntity = mapper.map(recognitionResult);
        recognitionResultRepository.save(recognitionResultEntity);
        return mapper.map(recognitionResultEntity);
    }

    @Override
    public RecognitionResultDTO updateItem(RecognitionResultDTO recognitionResult) {
        RecognitionResult recognitionResultEntity = recognitionResultRepository.findById(recognitionResult.getRecognitionResultId())
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        recognitionResultRepository.save(recognitionResultEntity);
        return recognitionResult;
    }

    @Override
    public RecognitionResultDTO deleteItem(Long id) {
        RecognitionResult recognitionResultEntity = recognitionResultRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        recognitionResultRepository.delete(recognitionResultEntity);
        return mapper.map(recognitionResultEntity);
    }

    @Override
    public List<RecognitionResultDTO> findAllByUserId(Long userId) {
        List<RecognitionResult> resultsByUser = recognitionResultRepository.findAllByCreateUserOrUpdateUser(userId, userId);
        return mapper.map(resultsByUser);
    }

    @Override
    public Map<LocalDateTime, List<RecognitionResultDTO>> findAllGroupedByDate() {
        List<RecognitionResult> recognitionResultList = recognitionResultRepository.findAll();
        List<RecognitionResultDTO> recognitionResultDTOList = mapper.map(recognitionResultList);
        Map<LocalDateTime, List<RecognitionResultDTO>> result = recognitionResultDTOList.stream()
                .collect(Collectors.groupingBy(item -> item.getCreateDate().truncatedTo(ChronoUnit.DAYS)));
        return new TreeMap<>(result);
    }
}