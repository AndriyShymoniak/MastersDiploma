package com.nulp.shymoniak.mastersproject.service;

public interface AbstractCrudValidator<DTO> {
    void checkIfValid(DTO dto);
}
