package com.acyuta.springsecuritywithjwt.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    @Id
    private String id;

    @NotBlank(message = "first.name.empty")
    @Size(max = 36)
    private String firstName;

    @NotBlank(message = "last.name.empty")
    @Size(max = 36)
    private String lastName;

    @Email(message = "invalid.email")
    @NotBlank(message = "email.empty")
    private String email;

    @NotBlank(message = "password.empty")
    @JsonIgnore
    private String password;

    private LocalDate dateOfBirth;

    private Boolean isActive = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
