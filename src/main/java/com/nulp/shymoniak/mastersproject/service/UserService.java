package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;

public interface UserService extends IAbstractCrudService<ApplicationUserDTO> {

    ApplicationUserDTO findByUsername(String username);

}
