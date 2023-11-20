package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roles")
@RequiredArgsConstructor
public class RoleController
{
    private final RoleService roleService;
}
