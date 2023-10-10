package com.techiesmeetups.web.controller;
import com.techiesmeetups.web.dto.UserInfoDTO;
import com.techiesmeetups.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UserInfoController {

    private UserInfoService userInfoService;
    private static final Logger logger = Logger.getLogger(UserInfoController.class.getName());

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/api/user/info/{userid}")
    public ResponseEntity<UserInfoDTO> getUserInformation(@PathVariable Long userid) {
        UserInfoDTO userInfoDTO = userInfoService.getUserInfo(userid);
        if(userInfoDTO != null) {
            return ResponseEntity.ok(userInfoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

//    @GetMapping("/api/user/info")
//    public ResponseEntity<UserInfoDTO> getUserInformation(@AuthenticationPrincipal UserDetails userDetails) {
//
//        String username = userDetails.getUsername();
//        logger.info("Username: " + username);
//
//
//        UserInfoDTO userInfoDTO = userInfoService.getUserInfoByUsername(username);
//        if(userInfoDTO != null) {
//            return ResponseEntity.ok(userInfoDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//
//    }
}
