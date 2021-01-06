package com.acyuta.springsecuritywithjwt.domain.service;

import com.acyuta.springsecuritywithjwt.domain.enums.RoleType;
import com.acyuta.springsecuritywithjwt.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleType roleType);
}
