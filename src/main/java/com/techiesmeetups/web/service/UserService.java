package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.RegistrationDTO;
import com.techiesmeetups.web.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDTO registrationDTO);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String userName);
}
