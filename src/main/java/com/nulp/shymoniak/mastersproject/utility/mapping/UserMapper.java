package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User, UserDTO> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
