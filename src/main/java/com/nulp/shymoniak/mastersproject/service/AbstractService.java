package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.utility.validator.Validator;

public abstract class AbstractService<T> {
    protected Validator validator;

    // TODO: 2/8/22 Validate using AOP
    protected void checkIfValid(T t){
        if (validator.isValid(t)) {
            throw new ApiRequestException(ApplicationConstants.ERROR_INVALID_ENTITY + t.toString());
        }
    }
}
