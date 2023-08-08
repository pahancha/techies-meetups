package com.techiesmeetups.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class RegistrationDTO {
    private long id;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
