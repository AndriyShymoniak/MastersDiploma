package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.AbstractRepository;
import com.nulp.shymoniak.mastersproject.mapping.AbstractMapper;
import com.nulp.shymoniak.mastersproject.validation.Validator;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Optional;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.ERROR_INVALID_ENTITY;
import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND;

public abstract class AbstractService<Entity, DTO > {
    protected Validator validator;
    protected AbstractRepository repository;
    protected AbstractMapper mapper;

    public Page<DTO> findAll(Pageable pageable) {
        Page<Entity> entityList = repository.findAll(pageable);
        Page<DTO> resultList = (Page<DTO>) mapper.mapToDTO(entityList);
        return resultList;
    }

    public DTO findById(Long id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        return optionalEntity.map(item -> (DTO) mapper.mapToDTO(item))
                .orElseThrow(() -> new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Transactional
    public DTO createItem(DTO dto) {
        Entity entity = (Entity) mapper.mapToEntity(dto);
        repository.save(entity);
        return (DTO) mapper.mapToDTO(entity);
    }

    @Transactional
    public DTO updateItem(DTO dto) {
        Entity entity = (Entity) mapper.mapToEntity(dto);
        Long entityId = getIdFromEntity(entity);
        if (!repository.existsById(entityId)) {
            throw new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND);
        }
        repository.save(entity);
        return (DTO) mapper.mapToDTO(entity);
    }

    @Transactional
    public DTO deleteItem(Long id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND);
        }
        repository.deleteById(id);
        return (DTO) mapper.mapToDTO(optionalEntity.get());
    }

    // TODO: AfterTrowing - return specified message in case of exception
    public void checkIfValid(DTO dto) {
        if (!validator.isValid(dto)) {
            throw new ApiRequestException(ERROR_INVALID_ENTITY + ": " + dto.toString());
        }
    }

    @SneakyThrows
    private Long getIdFromEntity(Entity entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Id idAnnotation = field.getAnnotation(Id.class);
            if (idAnnotation != null) {
                field.setAccessible(true);
                return (Long) field.get(entity);
            }
        }
        throw new ApiRequestException("There is no ID field found for entity: " + entity);
    }
}
