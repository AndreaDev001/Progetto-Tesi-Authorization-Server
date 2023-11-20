package com.tirocinio.authorizationserver.data.entities;


import com.tirocinio.authorizationserver.data.converters.TrimConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "CLIENTS")
@EntityListeners(value = AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "CLIENT_ID",nullable = false,unique = true)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    private String clientID;

    @Column(name = "CLIENT_SECRET",nullable = false)
    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ClientAuthenticationMethod> authenticationMethods = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<AuthorizationGrantType> authorizationGrantTypes;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> redirectUris;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> scopes;

    @Column(name = "PROOF_KEY",nullable = false)
    private boolean proofKey;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false)
    private LocalDate lastModifiedDate;

    public static RegisteredClient convert(Client client) {
        RegisteredClient.Builder builder = RegisteredClient.withId(client.getId().toString())
                .clientId(client.clientID)
                .clientSecret(client.clientSecret)
                .clientIdIssuedAt(Instant.now())
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(client.authenticationMethods))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(client.authorizationGrantTypes))
                .redirectUris(redirectUris -> redirectUris.addAll(client.getRedirectUris()))
                .scopes(scopes -> scopes.addAll(client.getScopes()))
                .clientSettings(ClientSettings.builder().requireProofKey(client.proofKey).build());
        return builder.build();
    }
}
