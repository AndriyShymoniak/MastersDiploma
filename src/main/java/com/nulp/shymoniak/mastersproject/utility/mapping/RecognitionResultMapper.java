package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.entity.RecognitionResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecognitionResultMapper {
    RecognitionResultMapper INSTANCE = Mappers.getMapper(RecognitionResultMapper.class);

    RecognitionResultDTO map(RecognitionResult recognitionResult);

    RecognitionResult map(RecognitionResultDTO recognitionResultDTO);

    List<RecognitionResultDTO> map(List<RecognitionResult> recognitionResultList);
}
