package com.tirocinio.authorizationserver.data.dto.output;


import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto extends GenericOutput<RoleDto>
{
    private UUID id;
    private String name;
    private LocalDate createdDate;
}
