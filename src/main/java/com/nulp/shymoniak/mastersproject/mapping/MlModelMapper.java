package com.nulp.shymoniak.mastersproject.mapping;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MlModelMapper extends AbstractMapper<MLModel, MLModelDTO> {
    MlModelMapper INSTANCE = Mappers.getMapper(MlModelMapper.class);
}
