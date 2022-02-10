package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.entity.ObservedObject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObservedObjectMapper {
    ObservedObjectMapper INSTANCE = Mappers.getMapper(ObservedObjectMapper.class);

    ObservedObjectDTO map(ObservedObject observedObject);

    ObservedObject map(ObservedObjectDTO observedObjectDTO);

    List<ObservedObjectDTO> map(List<ObservedObject> observedObjectList);
}
