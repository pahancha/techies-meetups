package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.LoginRequestDTO;
import com.techiesmeetups.web.dto.LoginResponseDTO;
import com.techiesmeetups.web.dto.RegistrationDTO;
import com.techiesmeetups.web.models.UserEntity;
import com.techiesmeetups.web.service.AuthService;
import com.techiesmeetups.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    AuthService authService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO user,
                                           BindingResult result) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", "DUPLICATE_EMAIL", "There is already a user with this email/username.");
        }
        UserEntity existingUsername = userService.findByUsername(user.getUserName());
        if (existingUsername != null && existingUsername.getUserName() != null && !existingUsername.getUserName().isEmpty()) {
            result.rejectValue("userName", "DUPLICATE_USERNAME", "There is already a user with this email/username.");
        }

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.loginUser(loginRequestDTO);
    }
}
