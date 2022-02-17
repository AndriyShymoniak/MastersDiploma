package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.entity.User;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.validation.RecognitionResultValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecognitionResultServiceImpl extends AbstractService<RecognitionResult, RecognitionResultDTO> implements RecognitionResultService {
    private final RecognitionResultRepository recognitionResultRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecognitionResultServiceImpl(RecognitionResultRepository repository, RecognitionResultValidator validator, UserRepository userRepository) {
        this.recognitionResultRepository = repository;
        this.repository = repository;
        this.userRepository = userRepository;
        this.validator = validator;
        this.mapper = RecognitionResultMapper.INSTANCE;
    }

    @Override
    public List<RecognitionResultDTO> findAllByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND);
        }
        List<RecognitionResult> resultsByUser = recognitionResultRepository.findAllByCreateUserOrUpdateUser(user.get(), user.get());
        return mapper.mapToDTO(resultsByUser);
    }

    @Override
    public Map<LocalDateTime, List<RecognitionResultDTO>> findAllGroupedByDate() {
        List<RecognitionResult> recognitionResultList = recognitionResultRepository.findAllForList();
        List<RecognitionResultDTO> recognitionResultDTOList = mapper.mapToDTO(recognitionResultList);
        Map<LocalDateTime, List<RecognitionResultDTO>> result = recognitionResultDTOList.stream()
                .collect(Collectors.groupingBy(item -> item.getCreateDate().truncatedTo(ChronoUnit.DAYS)));
        return new TreeMap<>(result);
    }
}