package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.data.dto.output.ClientDto;
import com.tirocinio.authorizationserver.services.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor

public class ClientController
{
    private final ClientService clientService;
}
