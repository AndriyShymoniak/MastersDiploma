package com.nulp.shymoniak.mastersproject.mapping;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.MLModel;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RecognitionResultMapper extends AbstractMapper<RecognitionResult, RecognitionResultDTO> {
    RecognitionResultMapper INSTANCE = Mappers.getMapper(RecognitionResultMapper.class);

    @Mappings({
            @Mapping(target = "observedObjectList", ignore = true),
            @Mapping(target = "mlModel", qualifiedByName = {"toDtoWithoutChildren"})
    })
    RecognitionResultDTO mapToDTO(RecognitionResult recognitionResult);


    @Named("toDtoWithoutChildren")
    @Mappings({
            @Mapping(target = "observedObjectList", ignore = true)
    })
    MLModelDTO internalMapperForDependentField(MLModel mlModel);
}
