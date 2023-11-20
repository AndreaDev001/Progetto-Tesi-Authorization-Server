package com.tirocinio.authorizationserver.data.dto.input;


import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClientDto
{
    @NotNull
    @NotBlank
    private String clientID;

    @NotNull
    @NotBlank
    private String clientSecret;

    @NotNull
    @NotEmpty
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @NotNull
    @NotEmpty
    private Set<AuthorizationGrantType> authorizationGrantTypes;

    @NotNull
    @NotEmpty
    private Set<String> redirectURIs;

    @NotNull
    @NotEmpty
    private Set<String> scopes;
}
