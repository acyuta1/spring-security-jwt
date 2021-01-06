package com.acyuta.springsecuritywithjwt.security.dto;

import com.acyuta.springsecuritywithjwt.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginResponse {

    private String jwt;

    private String username;

    private Set<RoleType> roles;

}
