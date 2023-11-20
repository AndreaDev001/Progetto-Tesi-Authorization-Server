package com.tirocinio.authorizationserver.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info =
@Info(title = "Authentication Server - Progetto Tirocinio",
description = "Documentazione Authorization Server",version = "1.0",
contact = @Contact(name = "Andrea Marchio",email = "marchioandrea02@gmail.com")))
public class OpenAPIConfig
{

}
