package com.techiesmeetups.web.security;

import com.techiesmeetups.web.models.UserEntity;
import com.techiesmeetups.web.repository.UserRepository;
import com.techiesmeetups.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public  CustomUserDetailsService(UserRepository userRepository) throws UsernameNotFoundException {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findFirstByUserName(username);
//        if (user != null) {
//            User authUser = new User(
//                    user.getEmail(),
//                    user.getPassword(),
//                    user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
//                            .collect(Collectors.toList())
//            );
//
//            return authUser;
//
//        } else {
//            throw new UsernameNotFoundException("Invalid Username or password");
//        }
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByUserName(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList())
            );
        } else {
            throw new UsernameNotFoundException("Invalid Username or password");
        }
    }


}
