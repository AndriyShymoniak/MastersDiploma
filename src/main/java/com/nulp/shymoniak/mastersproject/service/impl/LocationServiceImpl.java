package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.annotations.CrudService;
import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.entity.Location;
import com.nulp.shymoniak.mastersproject.repository.LocationRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.LocationService;
import com.nulp.shymoniak.mastersproject.mapping.LocationMapper;
import com.nulp.shymoniak.mastersproject.validation.LocationValidator;

@CrudService(
        validator = LocationValidator.class,
        repository = LocationRepository.class,
        mapper = LocationMapper.class
)
public class LocationServiceImpl extends AbstractService<Location, LocationDTO> implements LocationService {
}
