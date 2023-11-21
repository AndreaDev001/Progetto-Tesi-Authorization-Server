package com.tirocinio.authorizationserver.config;


import com.tirocinio.authorizationserver.data.dao.LocalUserDao;
import com.tirocinio.authorizationserver.data.entities.LocalUser;
import com.tirocinio.authorizationserver.data.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Configuration
@RequiredArgsConstructor
public class TokenConfiguration
{
    private final LocalUserDao localUserDao;

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            Authentication principal = context.getPrincipal();
            context.getClaims().claim("type",context.getTokenType().getValue());
            Set<String> roles = new HashSet<>();
            String provider = "LOCAL";
            if(principal instanceof UsernamePasswordAuthenticationToken) {
                LocalUser requiredUser = this.localUserDao.findById(UUID.fromString(principal.getName())).orElseThrow();
                provider = requiredUser.getProvider().toString();
                roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                context.getClaims().claim("username",requiredUser.getUsername());
                context.getClaims().claim("email",requiredUser.getEmail());
                context.getClaims().claim("name",requiredUser.getName());
                context.getClaims().claim("surname",requiredUser.getSurname());
            }
            context.getClaims().claim("provider",provider);
            context.getClaims().claim("roles",roles);
        };
    }
}
