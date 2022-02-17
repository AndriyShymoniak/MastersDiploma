package com.nulp.shymoniak.mastersproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface AbstractRepository<Entity, Long> extends PagingAndSortingRepository<Entity, Long> {
    @Override
    Page<Entity> findAll(Pageable pageable);
}
