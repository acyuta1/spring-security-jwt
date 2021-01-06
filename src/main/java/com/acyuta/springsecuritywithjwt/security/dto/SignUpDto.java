package com.acyuta.springsecuritywithjwt.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SignUpDto {

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
    @Size(min=4, max = 36)
    private String password;

    private LocalDate dateOfBirth;

}
