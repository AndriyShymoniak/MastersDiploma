package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MlModelMapper {
    MlModelMapper INSTANCE = Mappers.getMapper(MlModelMapper.class);

    MLModelDTO map(MLModel mlModel);

    MLModel map(MLModelDTO mlModelDTO);

    List<MLModelDTO> map(List<MLModel> mlModelList);
}
