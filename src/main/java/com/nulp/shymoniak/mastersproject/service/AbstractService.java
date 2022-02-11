package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.validation.Validator;

public abstract class AbstractService<T> {
    protected Validator validator;

    public void checkIfValid(T t){
        if (!validator.isValid(t)) {
            throw new ApiRequestException(ApplicationConstants.ERROR_INVALID_ENTITY + ": " + t.toString());
        }
    }
}
