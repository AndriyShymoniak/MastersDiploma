package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.entity.Person;
import com.nulp.shymoniak.mastersproject.repository.PersonRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.PersonService;
import com.nulp.shymoniak.mastersproject.utility.mapping.PersonMapper;
import com.nulp.shymoniak.mastersproject.validation.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends AbstractService<Person, PersonDTO> implements PersonService {

    @Autowired
    public PersonServiceImpl(PersonRepository repository, PersonValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = PersonMapper.INSTANCE;
    }
}
