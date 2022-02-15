package com.nulp.shymoniak.mastersproject.mapping;

import java.util.List;

public interface AbstractMapper<Entity, DTO> {
    DTO mapToDTO(Entity entity);

    Entity mapToEntity(DTO dto);

    List<DTO> mapToDTO(List<Entity> entityList);

    List<Entity> mapToEntity(List<DTO> dtoList);
}
