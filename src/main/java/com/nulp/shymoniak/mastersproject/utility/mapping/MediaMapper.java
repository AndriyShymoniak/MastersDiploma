package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.entity.Media;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    MediaMapper INSTANCE = Mappers.getMapper(MediaMapper.class);

    MediaDTO map(Media media);

    Media map(MediaDTO mediaDTO);

    List<MediaDTO> map(List<Media> mediaList);
}
