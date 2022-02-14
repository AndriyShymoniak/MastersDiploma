package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import com.nulp.shymoniak.mastersproject.repository.ObservedObjectRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.ObservedObjectService;
import com.nulp.shymoniak.mastersproject.utility.mapping.ObservedObjectMapper;
import com.nulp.shymoniak.mastersproject.validation.ObservedObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObservedObjectServiceImpl extends AbstractService<ObservedObject, ObservedObjectDTO> implements ObservedObjectService {

@Autowired
    public ObservedObjectServiceImpl(ObservedObjectRepository repository, ObservedObjectValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = ObservedObjectMapper.INSTANCE;
    }
}
