package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;

import java.util.List;
import java.util.Set;

public interface MLModelService {

    List<MLModelDTO> findAllModelsByObservedObject(Set<Long> observedObjectIdSet);

}
