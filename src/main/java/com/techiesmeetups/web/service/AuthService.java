package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.LoginRequestDTO;
import com.techiesmeetups.web.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);
}
