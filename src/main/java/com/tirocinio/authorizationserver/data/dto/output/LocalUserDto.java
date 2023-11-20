package com.tirocinio.authorizationserver.data.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalUserDto extends UserDto
{
    private String name;
    private String surname;
}
