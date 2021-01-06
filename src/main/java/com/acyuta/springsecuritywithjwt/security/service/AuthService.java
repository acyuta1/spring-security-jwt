package com.acyuta.springsecuritywithjwt.security.service;

import com.acyuta.springsecuritywithjwt.domain.model.User;
import com.acyuta.springsecuritywithjwt.security.dto.LoginDto;
import com.acyuta.springsecuritywithjwt.security.dto.LoginResponse;
import com.acyuta.springsecuritywithjwt.security.dto.SignUpDto;

public interface AuthService {

    LoginResponse login(LoginDto loginDto);

    User signUp(SignUpDto signUpDto);
}
