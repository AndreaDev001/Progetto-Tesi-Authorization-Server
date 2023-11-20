package com.tirocinio.authorizationserver.services.interfaces;

import com.tirocinio.authorizationserver.data.dto.input.CreateClientDto;
import com.tirocinio.authorizationserver.data.dto.output.ClientDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ClientService
{
    PagedModel<ClientDto> getClients(Pageable pageable);
    ClientDto getClient(UUID clientID);
    ClientDto getClient(String clientID);
    ClientDto createClient(CreateClientDto createClientDto);
    void deleteClient(UUID clientID);
}
