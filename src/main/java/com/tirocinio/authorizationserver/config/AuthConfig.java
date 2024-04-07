package com.tirocinio.authorizationserver.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.tirocinio.authorizationserver.federated.FederatedIdentityConfigurer;
import com.tirocinio.authorizationserver.federated.OAuth2UserHandler;
import com.tirocinio.authorizationserver.services.implementations.UserDetailsServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class AuthConfig
{
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final PasswordAuthenticationProvider passwordAuthenticationProvider;
    private final OAuth2UserHandler oAuth2UserHandler;
    private final CorsFilter testFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        httpSecurity.exceptionHandling((exceptions) -> exceptions
                .defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint("/login"),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                )
        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));
        httpSecurity.apply(new FederatedIdentityConfigurer());
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity httpSecurity) throws Exception {
        FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer()
                .oAuth2UserConsumer(oAuth2UserHandler);
        httpSecurity.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/documentation/**").permitAll()
                                .requestMatchers("/users/public/**").permitAll()
                                .requestMatchers("/local_users/public/**").permitAll()
                                .requestMatchers("/clients/public/**").permitAll()
                                .requestMatchers("/roles/public/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/local_users/createAccount").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/js/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(customizer -> {
                    customizer.loginPage("/login");
                }).csrf(AbstractHttpConfigurer::disable);
        httpSecurity.apply(federatedIdentityConfigurer);
        return httpSecurity.build();
    }
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer("http://localhost:9000").build();
    }
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRSAKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }
    private RSAKey generateRSAKey() {
        KeyPair keyPair = generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

    private KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try
        {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            keyPair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return keyPair;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(passwordAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsServiceImp;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService auth2AuthorizationConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }
}
