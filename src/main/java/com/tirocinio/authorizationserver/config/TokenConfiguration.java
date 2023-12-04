package com.tirocinio.authorizationserver.config;


import com.tirocinio.authorizationserver.data.dao.GoogleUserDao;
import com.tirocinio.authorizationserver.data.dao.LocalUserDao;
import com.tirocinio.authorizationserver.data.entities.Role;
import com.tirocinio.authorizationserver.data.entities.users.GoogleUser;
import com.tirocinio.authorizationserver.data.entities.users.LocalUser;
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
    private final GoogleUserDao googleUserDao;

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
            else if(principal instanceof OAuth2AuthenticationToken authenticationToken) {
                GoogleUser googleUser = this.googleUserDao.getUserByExternalID(authenticationToken.getPrincipal().getAttribute("sub")).orElseThrow();
                for(Role role : googleUser.getRoles()) {
                    String name = role.getName();
                    roles.add(name);
                }
                provider = googleUser.getProvider().toString();
                context.getClaims().claim("sub",googleUser.getId().toString());
                context.getClaims().claim("name",googleUser.getGivenName());
                context.getClaims().claim("surname",googleUser.getFamilyName());
                context.getClaims().claim("email",googleUser.getEmail());
                context.getClaims().claim("username",googleUser.getUsername());
            }
            context.getClaims().claim("provider",provider);
            context.getClaims().claim("roles",roles);
        };
    }
}
