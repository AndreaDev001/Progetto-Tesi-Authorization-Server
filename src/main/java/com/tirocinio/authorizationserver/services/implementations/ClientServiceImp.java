package com.tirocinio.authorizationserver.services.implementations;

import com.tirocinio.authorizationserver.data.dao.ClientDao;
import com.tirocinio.authorizationserver.data.entities.Client;
import com.tirocinio.authorizationserver.services.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ClientServiceImp implements RegisteredClientRepository, ClientService {

    private final ClientDao clientDao;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public void save(RegisteredClient registeredClient) {

    }
    @Override
    public RegisteredClient findById(String id) {
        Client client = this.clientDao.findById(UUID.fromString(id)).orElseThrow();
        return Client.convert(client);
    }
    @Override
    public RegisteredClient findByClientId(String clientID) {
        Client client = this.clientDao.getClientByID(clientID).orElseThrow();
        return Client.convert(client);
    }
}
