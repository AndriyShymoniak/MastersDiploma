package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.AbstractRepository;
import com.nulp.shymoniak.mastersproject.utility.CycleAvoidingMappingContext;
import com.nulp.shymoniak.mastersproject.mapping.AbstractMapper;
import com.nulp.shymoniak.mastersproject.validation.Validator;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<Entity, DTO> {
    protected Validator validator;
    protected AbstractRepository repository;
    protected AbstractMapper mapper;

    public List<DTO> findAll() {
        List<Entity> entityList = repository.findAll();
        return mapper.mapToDTO(entityList, CycleAvoidingMappingContext.getInstance());
    }

    public DTO findById(Long id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        return optionalEntity.map(item -> (DTO) mapper.mapToDTO(item, CycleAvoidingMappingContext.getInstance()))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Transactional
    public DTO createItem(DTO dto) {
        Entity entity = (Entity) mapper.mapToEntity(dto, CycleAvoidingMappingContext.getInstance());
        repository.save(entity);
        return (DTO) mapper.mapToDTO(entity, CycleAvoidingMappingContext.getInstance());
    }

    @Transactional
    public DTO updateItem(DTO dto) {
        Entity entity = (Entity) mapper.mapToEntity(dto, CycleAvoidingMappingContext.getInstance());
        Long entityId = getIdFromEntity(entity);
        if (!repository.existsById(entityId)) {
            throw new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND);
        }
        repository.save(entity);
        return dto;
    }

    @Transactional
    public DTO deleteItem(Long id) {
        Entity entity = (Entity) repository.findById(id);
        if (entity != null) {
            throw new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND);
        }
        repository.deleteById(id);
        return (DTO) mapper.mapToDTO(entity, CycleAvoidingMappingContext.getInstance());
    }

    public void checkIfValid(DTO DTO) {
        if (!validator.isValid(DTO)) {
            throw new ApiRequestException(ApplicationConstants.ERROR_INVALID_ENTITY + ": " + DTO.toString());
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
        throw new ApiRequestException("There is no ID field found for entity: " + entity.toString());
    }
}
