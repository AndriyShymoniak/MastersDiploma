package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.entity.Media;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MediaMapper extends AbstractMapper<Media, MediaDTO> {
    MediaMapper INSTANCE = Mappers.getMapper(MediaMapper.class);
}
