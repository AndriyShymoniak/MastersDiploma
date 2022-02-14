package com.nulp.shymoniak.mastersproject.mapping;

import com.nulp.shymoniak.mastersproject.utility.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

public interface AbstractMapper<Entity, DTO> {
    DTO mapToDTO(Entity entity, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    Entity mapToEntity(DTO dto, @Context CycleAvoidingMappingContext context);

    List<DTO> mapToDTO(List<Entity> entityList, @Context CycleAvoidingMappingContext context);
}
