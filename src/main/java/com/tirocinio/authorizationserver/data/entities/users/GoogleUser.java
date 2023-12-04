package com.tirocinio.authorizationserver.data.entities.users;


import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Table(name = "GOOGLE_USERS")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUser extends User {

    @Column(name = "EXTERNAL_ID",unique = true,nullable = false)
    private String externalID;

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "GIVEN_NAME",nullable = false)
    private String givenName;

    @Column(name = "FAMILY_NAME",nullable = false)
    private String familyName;

    public static GoogleUser fromOAuth2User(OAuth2User oAuth2User) {
        GoogleUser googleUser = new GoogleUser();
        googleUser.setExternalID(oAuth2User.getAttribute("sub").toString());
        googleUser.setUsername(oAuth2User.getAttribute("name").toString());
        googleUser.setEmail(oAuth2User.getAttribute("email").toString());
        googleUser.setName(oAuth2User.getAttribute("name").toString());
        googleUser.setGivenName(oAuth2User.getAttribute("given_name").toString());
        googleUser.setFamilyName(oAuth2User.getAttribute("family_name").toString());
        googleUser.setProvider(Provider.GOOGLE);
        return googleUser;
    }
}
