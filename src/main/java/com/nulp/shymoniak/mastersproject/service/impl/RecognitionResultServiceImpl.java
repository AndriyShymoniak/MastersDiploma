package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.utility.CycleAvoidingMappingContext;
import com.nulp.shymoniak.mastersproject.utility.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.validation.RecognitionResultValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecognitionResultServiceImpl extends AbstractService<RecognitionResult, RecognitionResultDTO> implements RecognitionResultService {
    private final RecognitionResultRepository recognitionResultRepository;


    public RecognitionResultServiceImpl(RecognitionResultRepository repository, RecognitionResultValidator validator) {
        this.recognitionResultRepository = repository;
        this.repository = repository;
        this.validator = validator;
        this.mapper = RecognitionResultMapper.INSTANCE;
    }

    @Override
    public List<RecognitionResultDTO> findAllByUserId(Long userId) {
        List<RecognitionResult> resultsByUser = recognitionResultRepository.findAllByCreateUserOrUpdateUser(userId, userId);
        return mapper.mapToDTO(resultsByUser, CycleAvoidingMappingContext.getInstance());
    }

    @Override
    public Map<LocalDateTime, List<RecognitionResultDTO>> findAllGroupedByDate() {
        List<RecognitionResult> recognitionResultList = recognitionResultRepository.findAll();
        List<RecognitionResultDTO> recognitionResultDTOList = mapper.mapToDTO(recognitionResultList, CycleAvoidingMappingContext.getInstance());
        Map<LocalDateTime, List<RecognitionResultDTO>> result = recognitionResultDTOList.stream()
                .collect(Collectors.groupingBy(item -> item.getCreateDate().truncatedTo(ChronoUnit.DAYS)));
        return new TreeMap<>(result);
    }
}