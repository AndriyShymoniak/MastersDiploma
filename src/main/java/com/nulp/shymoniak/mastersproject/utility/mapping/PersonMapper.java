package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO map(Person person);

    Person map(PersonDTO personDTO);

    List<PersonDTO> map(List<Person> personList);
}
