package com.techiesmeetups.web.service.impl;

import com.techiesmeetups.web.dto.LoginRequestDTO;
import com.techiesmeetups.web.dto.LoginResponseDTO;
import com.techiesmeetups.web.repository.RoleRepository;
import com.techiesmeetups.web.repository.UserRepository;
import com.techiesmeetups.web.security.CustomUserDetailsService;
import com.techiesmeetups.web.service.AuthService;
import com.techiesmeetups.web.service.TokenService;
import com.techiesmeetups.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword())
            );
            String token = tokenService.generateJwt(authentication);

            return new  LoginResponseDTO(
                    userDetailsService.loadUserByUsername(loginRequestDTO.getUserName()).getUsername(),
                    token,
                    userDetailsService.loadUserByUsername(loginRequestDTO.getUserName()).getAuthorities().toString(),
                    userRepository.findByUserName(userDetailsService.loadUserByUsername(loginRequestDTO.getUserName()).getUsername()).getId()
            );

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, null,"NOT REGISTERED",-1);
        }
    }


}
