package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.annotations.CrudService;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import com.nulp.shymoniak.mastersproject.repository.ObservedObjectRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.ObservedObjectService;
import com.nulp.shymoniak.mastersproject.mapping.ObservedObjectMapper;
import com.nulp.shymoniak.mastersproject.validation.ObservedObjectValidator;

@CrudService(
        validator = ObservedObjectValidator.class,
        repository = ObservedObjectRepository.class,
        mapper = ObservedObjectMapper.class
)
public class ObservedObjectServiceImpl extends AbstractService<ObservedObject, ObservedObjectDTO> implements ObservedObjectService {
}
