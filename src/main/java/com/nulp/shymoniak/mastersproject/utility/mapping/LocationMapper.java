package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO map(Location location);

    Location map(LocationDTO locationDTO);

    List<LocationDTO> map(List<Location> locationList);
}
