package com.techiesmeetups.web.service;

import com.techiesmeetups.web.dto.UserInfoDTO;

public interface UserInfoService {
    public UserInfoDTO getUserInfo(Long userid);

    UserInfoDTO getUserInfoByUsername(String username);
}
