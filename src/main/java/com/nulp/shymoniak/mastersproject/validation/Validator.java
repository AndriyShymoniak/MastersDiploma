package com.nulp.shymoniak.mastersproject.validation;

public interface Validator<T> {
    boolean isValid(T t);
}
