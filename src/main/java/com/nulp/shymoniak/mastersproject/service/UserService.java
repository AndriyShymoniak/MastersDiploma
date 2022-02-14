package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;

public interface UserService extends IAbstractCrudService<UserDTO> {

    UserDTO findByUsername(String username);

}
