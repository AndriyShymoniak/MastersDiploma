package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;

import java.util.List;
import java.util.Set;

public interface MLModelService {
    List<MLModelDTO> findAllModels();

    List<MLModelDTO> findAllModelsByObservedObject(Set<Long> observedObjectSet);

    MLModelDTO findModelById(Long modelId);

    MLModelDTO createModel(MLModelDTO mlModel);

    MLModelDTO deleteModel(Long modelId);

    MLModelDTO updateModel(MLModelDTO mlModel);
}
