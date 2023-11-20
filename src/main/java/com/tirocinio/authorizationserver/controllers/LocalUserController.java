package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.services.interfaces.LocalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/local_users")
@RequiredArgsConstructor
public class LocalUserController {
    private final LocalUserService localUserService;
}
