package com.tirocinio.authorizationserver;

import com.tirocinio.authorizationserver.data.dao.ClientDao;
import com.tirocinio.authorizationserver.data.dao.LocalUserDao;
import com.tirocinio.authorizationserver.data.dao.RoleDao;
import com.tirocinio.authorizationserver.data.entities.Client;
import com.tirocinio.authorizationserver.data.entities.LocalUser;
import com.tirocinio.authorizationserver.data.entities.Role;
import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;


import java.util.Set;

@SpringBootApplication
public class AuthorizationServerApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private LocalUserDao localUserDao;
	@Autowired
	private RoleDao roleDao;

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client client = Client.builder().clientID("client").clientSecret(passwordEncoder.encode("secret"))
				.authorizationGrantTypes(Set.of(AuthorizationGrantType.AUTHORIZATION_CODE,AuthorizationGrantType.CLIENT_CREDENTIALS,AuthorizationGrantType.REFRESH_TOKEN))
				.authenticationMethods(Set.of(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
				.redirectUris(Set.of("https://oauthdebugger.com/debug","https://oauth.pstmn.io/v1/callback"))
				.scopes(Set.of("openid"))
				.proofKey(true).build();
		Role userRole = Role.builder().name("ROLE_USER").build();
		Role adminRole = Role.builder().name("ROLE_ADMIN").build();
		LocalUser localUser = new LocalUser();
		localUser.setEmail("marchioandrea02@gmail.com");
		localUser.setUsername("andrea");
		localUser.setPassword(passwordEncoder.encode("password"));
		localUser.setName("Andrea");
		localUser.setSurname("Marchio");
		localUser.setExpired(false);
		localUser.setLocked(false);
		localUser.setProvider(Provider.LOCAL);
		localUser.setRoles(Set.of(userRole,adminRole));
		this.roleDao.save(userRole);
		this.roleDao.save(adminRole);
		this.clientDao.save(client);
		this.localUserDao.save(localUser);
	}
}
