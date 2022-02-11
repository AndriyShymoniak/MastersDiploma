package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ObservedObjectMapper extends AbstractMapper<ObservedObject, ObservedObjectDTO> {
    ObservedObjectMapper INSTANCE = Mappers.getMapper(ObservedObjectMapper.class);
}
