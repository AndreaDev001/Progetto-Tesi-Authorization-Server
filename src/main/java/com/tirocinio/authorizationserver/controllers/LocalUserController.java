package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.data.dto.input.CreateLocalUserDto;
import com.tirocinio.authorizationserver.data.dto.input.PaginationRequest;
import com.tirocinio.authorizationserver.data.dto.output.LocalUserDto;
import com.tirocinio.authorizationserver.services.interfaces.LocalUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/local_users")
@RequiredArgsConstructor
public class LocalUserController {

    private final LocalUserService localUserService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<LocalUserDto>> getUsers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        return ResponseEntity.ok(this.localUserService.getUsers(paginationRequest.toPageable()));
    }

    @GetMapping("/private/name/{name}/surname/{surname}")
    public ResponseEntity<PagedModel<LocalUserDto>> getUsers(@PathVariable("name") String name,@PathVariable("surname") String surname,@ParameterObject @Valid PaginationRequest paginationRequest) {
        return ResponseEntity.ok(this.localUserService.getUsers(name, surname, paginationRequest.toPageable()));
    }


    @GetMapping("/private/{userID}")
    public ResponseEntity<LocalUserDto> getUser(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.localUserService.getUser(userID));
    }

    @PostMapping("/createAccount")
    public void createUser(@ParameterObject @Valid CreateLocalUserDto createLocalUserDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try
        {
            this.localUserService.createUser(createLocalUserDto);
            response.sendRedirect("/login");
        }
        catch (Exception exception) {
            response.sendRedirect("/register?error='invalidUsername'");
        }
    }
}
