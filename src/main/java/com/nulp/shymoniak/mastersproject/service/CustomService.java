package com.nulp.shymoniak.mastersproject.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomService<T> {

    List<T> findAll();

    T findById(Long id);

    @Transactional
    T createItem(T t);

    @Transactional
    T updateItem(T t);

    @Transactional
    T deleteItem(Long id);
}
