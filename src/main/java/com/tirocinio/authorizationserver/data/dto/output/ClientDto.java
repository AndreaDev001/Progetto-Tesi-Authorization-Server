package com.tirocinio.authorizationserver.data.dto.output;


import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import lombok.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto extends GenericOutput<ClientDto>
{
    private UUID id;
    private String clientID;
    private String clientSecret;
    private Set<ClientAuthenticationMethod> authenticationMethods;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectURIs;
    private Set<String> scopes;
    private boolean proofKey;
    private LocalDate createdDate;
}
