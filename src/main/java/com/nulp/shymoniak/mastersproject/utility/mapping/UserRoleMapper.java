package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleDTO map(UserRole userRole);

    UserRole map(UserRoleDTO userRoleDTO);

    List<UserRoleDTO> map(List<UserRole> userRoleList);
}
