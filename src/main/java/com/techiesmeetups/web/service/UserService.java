package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.RegistrationDTO;
import com.techiesmeetups.web.models.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {
    void saveUser(RegistrationDTO registrationDTO);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String userName);

}
