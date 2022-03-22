package com.nulp.shymoniak.mastersproject.utility;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;

public interface SecuritySessionUtility {
    ApplicationUserDTO getCurrentUserFromSession();
}
