package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.MLModelRepository;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import com.nulp.shymoniak.mastersproject.utility.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MLModelServiceImpl implements MLModelService {
    private final MLModelRepository mlModelRepository;
    private final ObjectMapperUtils mapper;

    @Autowired
    public MLModelServiceImpl(MLModelRepository mlModelRepository, ObjectMapperUtils mapper) {
        this.mlModelRepository = mlModelRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MLModelDTO> findAllModels() {
        List<MLModel> modelList = mlModelRepository.findAll();
        return mapper.mapAll(modelList, MLModelDTO.class);
    }

    // TODO: 2022-01-04
    @Override
    public List<MLModelDTO> findAllModelsByObservedObject(ObservedObjectDTO observedObject) {
        return null;
    }

    @Override
    public MLModelDTO findModelById(Long modelId) {
        Optional<MLModel> optionalMLModel = mlModelRepository.findById(modelId);
        return optionalMLModel.map(item -> mapper.map(item, MLModelDTO.class))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

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
}