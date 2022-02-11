package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.MLModelRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import com.nulp.shymoniak.mastersproject.utility.mapping.MlModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MLModelServiceImpl extends AbstractService<MLModelDTO> implements MLModelService {
    private final MLModelRepository mlModelRepository;
    private final MlModelMapper mapper = MlModelMapper.INSTANCE;

    @Override
    public List<MLModelDTO> findAll() {
        List<MLModel> modelList = mlModelRepository.findAllActiveModels();
        return mapper.map(modelList);
    }

    @Override
    public MLModelDTO findById(Long id) {
        Optional<MLModel> optionalMLModel = mlModelRepository.findById(id);
        return optionalMLModel.map(item -> mapper.map(item))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    // TODO: 2/11/22 check if @Transactional is supported
    @Override
    public MLModelDTO createItem(MLModelDTO mlModel) {
        MLModel modelEntity = mapper.map(mlModel);
        mlModelRepository.save(modelEntity);
        return mapper.map(modelEntity);
    }

    @Override
    public MLModelDTO updateItem(MLModelDTO mlModel) {
        mlModelRepository.findById(mlModel.getMlModelId())
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        mlModelRepository.save(mapper.map(mlModel));
        return mlModel;
    }

    @Override
    public MLModelDTO deleteItem(Long id) {
        MLModel modelEntity = mlModelRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        modelEntity.setIsActive(ApplicationConstants.DEFAULT_FALSE_FLAG);
        mlModelRepository.save(modelEntity);
        return mapper.map(modelEntity);
    }

    @Override
    public List<MLModelDTO> findAllModelsByObservedObject(Set<Long> observedObjectIdSet) {
        List<MLModel> modelList = mlModelRepository.findAll();
        modelList = modelList.stream()
                .filter(model -> model.getIsActive().equals(ApplicationConstants.DEFAULT_TRUE_FLAG))
                .filter(model -> doesModelContainAllObservedObjects(model, observedObjectIdSet))
                .collect(Collectors.toList());
        return mapper.map(modelList);
    }

    private boolean doesModelContainAllObservedObjects(MLModel model, Set<Long> observedObjectIdSet) {
        return model.getObservedObjectList().stream()
                .map(observedObject -> observedObject.getObservedObject().getObservedObjectId())
                .collect(Collectors.toList())
                .containsAll(observedObjectIdSet);
    }
}