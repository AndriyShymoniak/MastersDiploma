package com.nulp.shymoniak.mastersproject.repository;

import com.nulp.shymoniak.mastersproject.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends AbstractRepository<Person, Long> {
}
