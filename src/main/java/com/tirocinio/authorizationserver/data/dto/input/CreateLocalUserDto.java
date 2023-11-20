package com.tirocinio.authorizationserver.data.dto.input;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateLocalUserDto
{
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    @Length(min = 3,max = 10)
    private String name;
    @NotNull
    @NotBlank
    @Length(min = 3,max = 10)
    private String surname;
}
