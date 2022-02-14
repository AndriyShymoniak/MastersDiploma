package com.nulp.shymoniak.mastersproject.service;

import java.util.List;

public interface IAbstractCrudService<DTO> {

    List<DTO> findAll();

    DTO findById(Long id);

    DTO createItem(DTO dto);

    DTO updateItem(DTO dto);

    DTO deleteItem(Long id);
}
