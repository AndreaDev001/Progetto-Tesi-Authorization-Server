package com.tirocinio.authorizationserver.federated;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.function.Consumer;

@Data
public class FederatedIdentityAuthenticationHandler implements AuthenticationSuccessHandler
{
    private final AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();
    private Consumer<OAuth2User> oAuth2UserConsumer = oAuth2User -> {};
    private Consumer<OidcUser> oidcUserConsumer = oidcUser -> this.oAuth2UserConsumer.accept(oidcUser);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication instanceof OAuth2AuthenticationToken) {
            if(authentication.getPrincipal() instanceof OidcUser oidcUser)
                oidcUserConsumer.accept(oidcUser);
            else if(authentication.getPrincipal() instanceof OAuth2User oAuth2User)
                oAuth2UserConsumer.accept(oAuth2User);
        }
        this.delegate.onAuthenticationSuccess(request,response,authentication);
    }
}