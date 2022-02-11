package com.nulp.shymoniak.mastersproject.utility.mapping;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends AbstractMapper<UserRole, UserRoleDTO> {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);
}
