package com.acyuta.springsecuritywithjwt.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "username.is.empty")
    private String username;

    @NotBlank(message = "password.is.empty")
    private String password;
}
