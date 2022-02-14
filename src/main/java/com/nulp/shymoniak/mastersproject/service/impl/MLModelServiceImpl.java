package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.repository.MLModelRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import com.nulp.shymoniak.mastersproject.utility.CycleAvoidingMappingContext;
import com.nulp.shymoniak.mastersproject.utility.mapping.MlModelMapper;
import com.nulp.shymoniak.mastersproject.validation.MLModelValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MLModelServiceImpl extends AbstractService<MLModel, MLModelDTO> implements MLModelService {
    private final MLModelRepository mlModelRepository;


    public MLModelServiceImpl(MLModelRepository repository, MLModelValidator validator) {
        this.mlModelRepository = repository;
        this.repository = repository;
        this.validator = validator;
        this.mapper = MlModelMapper.INSTANCE;
    }

    // TODO: 2/11/22 check if @Transactional is supported

    @Override
    public List<MLModelDTO> findAllModelsByObservedObject(Set<Long> observedObjectIdSet) {
        List<MLModel> modelList = mlModelRepository.findAll();
        modelList = modelList.stream()
                .filter(model -> model.getIsActive().equals(ApplicationConstants.DEFAULT_TRUE_FLAG))
                .filter(model -> doesModelContainAllObservedObjects(model, observedObjectIdSet))
                .collect(Collectors.toList());
        return mapper.mapToDTO(modelList, CycleAvoidingMappingContext.getInstance());
    }

    private boolean doesModelContainAllObservedObjects(MLModel model, Set<Long> observedObjectIdSet) {
        return model.getObservedObjectList().stream()
                .map(observedObject -> observedObject.getObservedObject().getObservedObjectId())
                .collect(Collectors.toList())
                .containsAll(observedObjectIdSet);
    }
}