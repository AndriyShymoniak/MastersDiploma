package com.nulp.shymoniak.mastersproject.annotations;

import com.nulp.shymoniak.mastersproject.mapping.AbstractMapper;
import com.nulp.shymoniak.mastersproject.repository.AbstractRepository;
import com.nulp.shymoniak.mastersproject.validation.Validator;
import org.springframework.stereotype.Service;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Service
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CrudService {
    Class<? extends Validator> validator();

    Class<? extends AbstractRepository> repository();

    Class<? extends AbstractMapper> mapper();
}
