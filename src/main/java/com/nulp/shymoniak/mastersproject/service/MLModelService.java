package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;

import java.util.List;

public interface MLModelService {
    List<MLModelDTO> findAllModels();

    List<MLModelDTO> findAllModelsByObservedObject(ObservedObjectDTO observedObject);

    MLModelDTO findModelById(Long id);

    MLModelDTO createModel(MLModelDTO mlModel);

    MLModelDTO deleteModel(Long modelId);

    MLModelDTO updateModel(MLModelDTO mlModel);
}
