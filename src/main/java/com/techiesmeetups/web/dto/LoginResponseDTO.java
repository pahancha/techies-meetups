package com.techiesmeetups.web.dto;

import com.techiesmeetups.web.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String user;
    private String jwt;

}
