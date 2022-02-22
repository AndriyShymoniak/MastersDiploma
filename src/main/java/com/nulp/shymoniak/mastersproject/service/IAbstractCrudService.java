package com.nulp.shymoniak.mastersproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAbstractCrudService<DTO > {
    Page<DTO> findAll(Pageable pageable);

    DTO findById(Long id);

    DTO createItem(DTO dto);

    DTO updateItem(DTO dto);

    DTO deleteItem(Long id);
}
