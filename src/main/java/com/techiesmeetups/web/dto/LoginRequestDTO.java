package com.techiesmeetups.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
}

