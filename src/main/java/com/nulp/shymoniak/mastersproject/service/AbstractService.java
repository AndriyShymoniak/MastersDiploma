package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.AbstractRepository;
import com.nulp.shymoniak.mastersproject.utility.mapping.AbstractMapper;
import com.nulp.shymoniak.mastersproject.validation.Validator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<Entity, DTO> {
    // TODO: 2/11/22 make protected
    public Validator validator;
    public AbstractRepository repository;
    public AbstractMapper mapper;

    public List<DTO> findAll() {
        List<Entity> entityList = repository.findAll();
        return mapper.mapToDTO(entityList);
    }

    public DTO findById(Long id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        return optionalEntity.map(item -> (DTO) mapper.mapToEntity(item))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Transactional
    public DTO createItem(DTO dto) {
        Entity entity = (Entity) mapper.mapToEntity(dto);
        repository.save(entity);
        return (DTO) mapper.mapToDTO(entity);
    }

    // TODO: 2/11/22 finish method
    @Transactional
    public DTO updateItem(DTO dto) {
        //        userRepository.findById(user.getUserId())
        //                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        repository.save(mapper.mapToEntity(dto));
        return dto;
    }

    // TODO: 2/11/22
    @Transactional
    public DTO deleteItem(Long id) {
        //         User userEntity = userRepository.findById(id)
        //                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        //        userRepository.delete(userEntity);
        //        return mapper.map(userEntity);
        return null;
    }

    public void checkIfValid(DTO DTO) {
        if (!validator.isValid(DTO)) {
            throw new ApiRequestException(ApplicationConstants.ERROR_INVALID_ENTITY + ": " + DTO.toString());
        }
    }
}
