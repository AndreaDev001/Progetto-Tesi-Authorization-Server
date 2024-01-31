package com.tirocinio.authorizationserver.federated;

import com.tirocinio.authorizationserver.data.dao.GoogleUserDao;
import com.tirocinio.authorizationserver.data.dao.RoleDao;
import com.tirocinio.authorizationserver.data.entities.Role;
import com.tirocinio.authorizationserver.data.entities.users.GoogleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class OAuth2UserHandler implements Consumer<OAuth2User> {

    private final GoogleUserDao googleUserDao;
    private final RoleDao roleDao;

    @Override
    public void accept(OAuth2User user) {
        Role memberRole = this.roleDao.getRoleByName("ROLE_MEMBER").orElseThrow();
        String sub = (String)user.getAttribute("sub");
        Optional<GoogleUser> googleUserOptional = this.googleUserDao.getUserByExternalID(sub);
        if(googleUserOptional.isEmpty()) {
            GoogleUser googleUser = GoogleUser.fromOAuth2User(user);
            googleUser.setRoles(Set.of(memberRole));
            this.googleUserDao.save(googleUser);
        }
    }
}
