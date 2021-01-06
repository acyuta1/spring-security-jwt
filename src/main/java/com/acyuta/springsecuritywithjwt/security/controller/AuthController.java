package com.acyuta.springsecuritywithjwt.security.controller;

import com.acyuta.springsecuritywithjwt.domain.model.User;
import com.acyuta.springsecuritywithjwt.security.dto.LoginDto;
import com.acyuta.springsecuritywithjwt.security.dto.LoginResponse;
import com.acyuta.springsecuritywithjwt.security.dto.SignUpDto;
import com.acyuta.springsecuritywithjwt.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/signUp")
    public User signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return authService.signUp(signUpDto);
    }
}
