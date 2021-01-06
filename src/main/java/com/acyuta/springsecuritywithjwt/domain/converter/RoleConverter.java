package com.acyuta.springsecuritywithjwt.domain.converter;

import com.acyuta.springsecuritywithjwt.domain.enums.RoleType;
import com.acyuta.springsecuritywithjwt.domain.model.Role;
import com.acyuta.springsecuritywithjwt.domain.service.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class RoleConverter implements Converter<RoleType, Role> {

    private final RoleRepository roleRepository;

    @Override
    public Role convert(RoleType roleType) {
        return roleRepository.findByName(roleType).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "role.invalid"));
    }
}