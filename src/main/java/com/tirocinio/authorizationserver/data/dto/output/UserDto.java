package com.tirocinio.authorizationserver.data.dto.output;


import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends GenericOutput<UserDto> {
    protected UUID id;
    protected String email;
    protected String username;
    protected Provider provider;
    protected LocalDate createdDate;
}
