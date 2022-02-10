package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO map(User user);

    User map(UserDTO userDTO);

    List<UserDTO> map(List<User> userList);
}
