package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.entity.UserRole;
import com.nulp.shymoniak.mastersproject.repository.UserRoleRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.UserRoleService;
import com.nulp.shymoniak.mastersproject.mapping.UserRoleMapper;
import com.nulp.shymoniak.mastersproject.validation.UserRoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends AbstractService<UserRole, UserRoleDTO> implements UserRoleService {

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository repository, UserRoleValidator validator) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = UserRoleMapper.INSTANCE;
    }
}
