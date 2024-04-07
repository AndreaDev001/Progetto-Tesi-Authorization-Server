package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.data.dto.input.CreateClientDto;
import com.tirocinio.authorizationserver.data.dto.input.PaginationRequest;
import com.tirocinio.authorizationserver.data.dto.output.ClientDto;
import com.tirocinio.authorizationserver.data.dto.output.RoleDto;
import com.tirocinio.authorizationserver.services.interfaces.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
public class ClientController
{
    private final ClientService clientService;

    @GetMapping("/private")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ClientDto>> getClients(@ParameterObject @Valid PaginationRequest paginationRequest) {
        return ResponseEntity.ok(this.clientService.getClients(paginationRequest.toPageable()));
    }

    @GetMapping("/private/{clientID}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClientDto> getClient(@PathVariable("clientID") UUID clientID) {
        return ResponseEntity.ok(this.clientService.getClient(clientID));
    }

    @GetMapping("/private/id/{clientID}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClientDto> getClient(@PathVariable("clientID") String clientID) {
        return ResponseEntity.ok(this.clientService.getClient(clientID));
    }

    @PostMapping("/private")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid CreateClientDto createClientDto) {
        return ResponseEntity.status(201).body(this.clientService.createClient(createClientDto));
    }

    @DeleteMapping("/private/{clientID}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientID") UUID clientID) {
        this.clientService.deleteClient(clientID);
        return ResponseEntity.noContent().build();
    }
}
