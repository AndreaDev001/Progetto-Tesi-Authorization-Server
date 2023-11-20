package com.tirocinio.authorizationserver.services.implementations;

import com.tirocinio.authorizationserver.data.dao.ClientDao;
import com.tirocinio.authorizationserver.data.dto.input.CreateClientDto;
import com.tirocinio.authorizationserver.data.dto.output.ClientDto;
import com.tirocinio.authorizationserver.data.entities.Client;
import com.tirocinio.authorizationserver.services.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ClientServiceImp extends GenericServiceImp<Client,ClientDto> implements RegisteredClientRepository, ClientService {

    private final ClientDao clientDao;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImp(ClientDao clientDao,PasswordEncoder passwordEncoder,ModelMapper modelMapper,PagedResourcesAssembler<Client> pagedResourcesAssembler) {
        super(modelMapper, Client.class,ClientDto.class, pagedResourcesAssembler);
        this.clientDao = clientDao;
        this.passwordEncoder = passwordEncoder;
    }

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

    @Override
    public PagedModel<ClientDto> getClients(Pageable pageable) {
        Page<Client> clients = this.clientDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(clients,modelAssembler);
    }

    @Override
    public ClientDto getClient(UUID clientID) {
        Client client = this.clientDao.findById(clientID).orElseThrow();
        return this.modelMapper.map(client,ClientDto.class);
    }

    @Override
    public ClientDto getClient(String clientID) {
        Client client = this.clientDao.getClientByID(clientID).orElseThrow();
        return this.modelMapper.map(client,ClientDto.class);
    }

    @Override
    public ClientDto createClient(CreateClientDto createClientDto) {
        return null;
    }

    @Override
    public void deleteClient(UUID clientID) {
        this.clientDao.findById(clientID).orElseThrow();
        this.clientDao.deleteById(clientID);
    }
}
