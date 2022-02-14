package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.entity.Location;
import com.nulp.shymoniak.mastersproject.repository.LocationRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.LocationService;
import com.nulp.shymoniak.mastersproject.utility.mapping.LocationMapper;
import com.nulp.shymoniak.mastersproject.validation.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends AbstractService<Location, LocationDTO> implements LocationService {

    @Autowired
    public LocationServiceImpl(LocationRepository repository, LocationValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = LocationMapper.INSTANCE;
    }
}
