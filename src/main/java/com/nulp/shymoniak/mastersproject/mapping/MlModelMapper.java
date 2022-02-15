package com.nulp.shymoniak.mastersproject.mapping;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Named("MlModelMapper")
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MlModelMapper extends AbstractMapper<MLModel, MLModelDTO> {
    MlModelMapper INSTANCE = Mappers.getMapper(MlModelMapper.class);

    @Named("toDtoWithoutChildren")
    @Mappings({
            @Mapping(target = "observedObjectList", ignore = true)
    })
    MLModelDTO mapToDTO(MLModel mlModel);
}
