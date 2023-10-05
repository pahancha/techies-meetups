package com.techiesmeetups.web.controller;
import com.techiesmeetups.web.dto.UserInfoDTO;
import com.techiesmeetups.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    private UserInfoService userInfoService;

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
}
