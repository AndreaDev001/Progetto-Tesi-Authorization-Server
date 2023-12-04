package com.tirocinio.authorizationserver.federated;

import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.function.Consumer;

public class FederatedIdentityConfigurer extends AbstractHttpConfigurer<FederatedIdentityConfigurer, HttpSecurity>
{
    public String loginPageURL = "/login";
    private String authorizationRequestURI;
    private Consumer<OAuth2User> oAuth2UserConsumer;
    private Consumer<OidcUser> oidcUserConsumer;

    public FederatedIdentityConfigurer loginPageURL(String loginPageURL) {
        this.loginPageURL = loginPageURL;
        return this;
    }

    public FederatedIdentityConfigurer authorizationRequestURI(String authorizationRequestURI) {
        this.authorizationRequestURI = authorizationRequestURI;
        return this;
    }

    public FederatedIdentityConfigurer oAuth2UserConsumer(Consumer<OAuth2User> oAuth2UserConsumer) {
        this.oAuth2UserConsumer = oAuth2UserConsumer;
        return this;
    }

    public FederatedIdentityConfigurer oidcUserConsumer(Consumer<OidcUser> oidcUserConsumer) {
        this.oidcUserConsumer = oidcUserConsumer;
        return this;
    }

    @Override
    public void init(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        ClientRegistrationRepository clientRegistrationRepository = applicationContext.getBean(ClientRegistrationRepository.class);
        FederatedIdentityAuthenticationEntryPoint federatedIdentityAuthenticationEntryPoint = new FederatedIdentityAuthenticationEntryPoint(this.loginPageURL,clientRegistrationRepository);
        if(this.authorizationRequestURI != null)
            federatedIdentityAuthenticationEntryPoint.setAuthorizationRequestURI(this.authorizationRequestURI);
        FederatedIdentityAuthenticationHandler authenticationHandler = new FederatedIdentityAuthenticationHandler();
        if(this.oidcUserConsumer != null)
            authenticationHandler.setOidcUserConsumer(this.oidcUserConsumer);
        if(this.oAuth2UserConsumer != null)
            authenticationHandler.setOAuth2UserConsumer(this.oAuth2UserConsumer);
        http.exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(federatedIdentityAuthenticationEntryPoint))
                .oauth2Login(oauth2Login -> {
                    oauth2Login.successHandler(authenticationHandler);
                    if(this.authorizationRequestURI != null)
                    {
                        String baseURI = this.authorizationRequestURI.replace("/{registrationId}","");
                        oauth2Login.authorizationEndpoint(endpoint -> endpoint.baseUri(baseURI));
                    }
                });
    }
}
