package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;
}
