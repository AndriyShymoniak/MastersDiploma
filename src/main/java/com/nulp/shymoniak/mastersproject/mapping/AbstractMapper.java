package com.nulp.shymoniak.mastersproject.mapping;

import org.springframework.data.domain.Page;

import java.util.List;

public interface AbstractMapper<Entity, DTO> {
    DTO mapToDTO(Entity entity);

    Entity mapToEntity(DTO dto);

    List<DTO> mapToDTO(List<Entity> entityList);

    List<Entity> mapToEntity(List<DTO> dtoList);

    default Page<DTO> mapToDTO(Page<Entity> entityPage) {
        if (entityPage == null) {
            return null;
        }
        return entityPage.map(this::mapToDTO);
    }
}
