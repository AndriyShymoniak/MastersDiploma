package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.annotations.CrudService;
import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.RecognitionResultRepository;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import com.nulp.shymoniak.mastersproject.mapping.RecognitionResultMapper;
import com.nulp.shymoniak.mastersproject.validation.RecognitionResultValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@CrudService(
        validator = RecognitionResultValidator.class,
        repository = RecognitionResultRepository.class,
        mapper = RecognitionResultMapper.class
)
@RequiredArgsConstructor
public class RecognitionResultServiceImpl extends AbstractService<RecognitionResult, RecognitionResultDTO> implements RecognitionResultService {
    @NonNull
    private RecognitionResultRepository recognitionResultRepository;
    @NonNull
    private ApplicationUserRepository userRepository;

    @Override
    public List<RecognitionResultDTO> findAllByUserId(Long userId) {
        Optional<ApplicationUser> user = userRepository.findById(userId);
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