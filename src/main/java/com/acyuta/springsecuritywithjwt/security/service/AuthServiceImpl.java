package com.acyuta.springsecuritywithjwt.security.service;

import com.acyuta.springsecuritywithjwt.domain.converter.RoleConverter;
import com.acyuta.springsecuritywithjwt.domain.enums.RoleType;
import com.acyuta.springsecuritywithjwt.domain.model.User;
import com.acyuta.springsecuritywithjwt.domain.service.UserRepository;
import com.acyuta.springsecuritywithjwt.security.UserDetailsImpl;
import com.acyuta.springsecuritywithjwt.security.dto.LoginDto;
import com.acyuta.springsecuritywithjwt.security.dto.LoginResponse;
import com.acyuta.springsecuritywithjwt.security.dto.SignUpDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final RoleConverter roleConverter;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginDto loginDto) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var secret = Keys.hmacShaKeyFor("this is a large secret key.this is a large secret key.".getBytes(StandardCharsets.UTF_8));
//        var secret = Keys.secretKeyFor(SignatureAlgorithm.ES512);

        var jwt = Jwts.builder()
                .signWith(secret)
                .setIssuer("Acyuta")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 3000000))
                .setSubject("JWT Token")
                .claim("username", userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .compact();

        return new LoginResponse()
                .setJwt(jwt)
                .setUsername(userDetails.getUsername())
                .setRoles(userDetails.getAuthorities().stream().map(a -> RoleType.valueOf(a.getAuthority())).collect(Collectors.toSet()));
    }

    @Override
    public User signUp(SignUpDto signUpDto) {
        if(userRepository.existsByEmail(signUpDto.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user.already.exists");

        var user = new User();
        user
                .setId(UUID.randomUUID().toString())
                .setDateOfBirth(signUpDto.getDateOfBirth())
                .setEmail(signUpDto.getEmail())
                .setFirstName(signUpDto.getFirstName())
                .setLastName(signUpDto.getLastName())
                .setRoles(Set.of(roleConverter.convert(RoleType.ROLE_USER)))
                .setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        return userRepository.save(user);

    }
}
