package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.MLModelRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import com.nulp.shymoniak.mastersproject.utility.ObjectMapperUtils;
import com.nulp.shymoniak.mastersproject.validation.MLModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MLModelServiceImpl extends AbstractService<MLModelDTO> implements MLModelService {
    private final MLModelRepository mlModelRepository;
    private final ObjectMapperUtils mapper;

    @Autowired
    public MLModelServiceImpl(MLModelRepository mlModelRepository, ObjectMapperUtils mapper, MLModelValidator validator) {
        this.mlModelRepository = mlModelRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<MLModelDTO> findAllModels() {
        List<MLModel> modelList = mlModelRepository.findAll();
        return mapper.mapAll(modelList, MLModelDTO.class);
    }

    @Override
    public List<MLModelDTO> findAllModelsByObservedObject(Set<Long> observedObjectIdSet) {
        List<MLModel> modelList = mlModelRepository.findAll();
        modelList = modelList.stream()
                .filter(model -> doesModelContainAllObservedObjects(model, observedObjectIdSet))
                .collect(Collectors.toList());
        return mapper.mapAll(modelList, MLModelDTO.class);
    }

    @Override
    public MLModelDTO findModelById(Long modelId) {
        Optional<MLModel> optionalMLModel = mlModelRepository.findById(modelId);
        return optionalMLModel.map(item -> mapper.map(item, MLModelDTO.class))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    // TODO: 2/7/22 fill createDate, createUser
    @Transactional
    @Override
    public MLModelDTO createModel(MLModelDTO mlModel) {
        MLModel modelEntity = mapper.map(mlModel, MLModel.class);
        mlModelRepository.save(modelEntity);
        return mapper.map(modelEntity, MLModelDTO.class);
    }

    @Transactional
    @Override
    public MLModelDTO deleteModel(Long modelId) {
        MLModel modelEntity = mlModelRepository.findById(modelId)
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        mlModelRepository.delete(modelEntity);
        return mapper.map(modelEntity, MLModelDTO.class);
    }

    @Transactional
    @Override
    public MLModelDTO updateModel(MLModelDTO mlModel) {
        mlModelRepository.findById(mlModel.getMlModelId())
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        mlModelRepository.save(mapper.map(mlModel, MLModel.class));
        return mlModel;
    }

    private boolean doesModelContainAllObservedObjects(MLModel model, Set<Long> observedObjectIdSet) {
        return model.getObservedObjectList().stream()
                .map(observedObject -> observedObject.getObservedObject().getObservedObjectId())
                .collect(Collectors.toList())
                .containsAll(observedObjectIdSet);
    }
}