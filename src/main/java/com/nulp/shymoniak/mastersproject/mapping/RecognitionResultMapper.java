package com.nulp.shymoniak.mastersproject.mapping;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecognitionResultMapper extends AbstractMapper<RecognitionResult, RecognitionResultDTO> {
    RecognitionResultMapper INSTANCE = Mappers.getMapper(RecognitionResultMapper.class);
}
