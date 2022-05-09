package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.annotations.CrudService;
import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.entity.Person;
import com.nulp.shymoniak.mastersproject.repository.PersonRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.PersonService;
import com.nulp.shymoniak.mastersproject.mapping.PersonMapper;
import com.nulp.shymoniak.mastersproject.validation.PersonValidator;

@CrudService(
        validator = PersonValidator.class,
        repository = PersonRepository.class,
        mapper = PersonMapper.class
)
public class PersonServiceImpl extends AbstractService<Person, PersonDTO> implements PersonService {
}
