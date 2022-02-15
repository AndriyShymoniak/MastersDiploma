package com.nulp.shymoniak.mastersproject.mapping;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Named("ObservedObjectMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ObservedObjectMapper extends AbstractMapper<ObservedObject, ObservedObjectDTO> {
    ObservedObjectMapper INSTANCE = Mappers.getMapper(ObservedObjectMapper.class);

    @Mappings({
            @Mapping(target = "mlModelList", ignore = true),
            @Mapping(target = "recognitionResultList", ignore = true)
    })
    ObservedObjectDTO mapToDTO(ObservedObject observedObject);
}
