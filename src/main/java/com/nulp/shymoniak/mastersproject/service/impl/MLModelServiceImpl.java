package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.mapping.MLModelMapper;
import com.nulp.shymoniak.mastersproject.repository.MLModelRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import com.nulp.shymoniak.mastersproject.validation.MLModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MLModelServiceImpl extends AbstractService<MLModel, MLModelDTO> implements MLModelService {

    @Autowired
    public MLModelServiceImpl(MLModelRepository repository, MLModelValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = MLModelMapper.INSTANCE;
    }

    @Override
    public List<MLModelDTO> findAllModelsByObservedObject(Set<Long> observedObjectIdSet) {
        List<MLModel> modelList = repository.findAll();
        modelList = modelList.stream()
                .filter(model -> model.getIsActive().equals(ApplicationConstants.DEFAULT_TRUE_FLAG))
                .filter(model -> doesModelContainAllObservedObjects(model, observedObjectIdSet))
                .collect(Collectors.toList());
        return mapper.mapToDTO(modelList);
    }

    private boolean doesModelContainAllObservedObjects(MLModel model, Set<Long> observedObjectIdSet) {
        return model.getObservedObjectList().stream()
                .map(observedObject -> observedObject.getObservedObject().getObservedObjectId())
                .collect(Collectors.toList())
                .containsAll(observedObjectIdSet);
    }
}