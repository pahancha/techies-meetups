package com.techiesmeetups.web.controller;

import com.techiesmeetups.web.dto.LoginRequestDTO;
import com.techiesmeetups.web.dto.RegistrationDTO;
import com.techiesmeetups.web.models.UserEntity;
import com.techiesmeetups.web.security.CustomUserDetailsService;
import com.techiesmeetups.web.service.UserService;
import jakarta.validation.Valid;
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
    private UserService userService;

    CustomUserDetailsService customUserDetailsService;

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

//    @PostMapping("/api/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUserName(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return ResponseEntity.ok("User logged in successfully");
//    }

//    @PostMapping("/api/login")
//    public ResponseEntity<String> login(@RequestParam String userName,
//                                        @RequestParam String password) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userName, password)
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return ResponseEntity.ok("User logged in successfully");
//        } catch (AuthenticationException e) {
//            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
//        }
//    }





}
