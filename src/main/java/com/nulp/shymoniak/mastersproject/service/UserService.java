package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;

import java.util.List;

public interface UserService extends CustomService<UserDTO> {

    UserDTO findByUsername(String username);

}
