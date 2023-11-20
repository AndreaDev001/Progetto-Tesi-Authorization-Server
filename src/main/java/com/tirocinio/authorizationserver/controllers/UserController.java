package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.data.dto.input.PaginationRequest;
import com.tirocinio.authorizationserver.data.dto.output.UserDto;
import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import com.tirocinio.authorizationserver.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<UserDto>> getUsers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        return ResponseEntity.ok(this.userService.getUsers(paginationRequest.toPageable()));
    }

    @GetMapping("/private/{userID}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.userService.getUser(userID));
    }

    @GetMapping("/public/providers")
    public ResponseEntity<CollectionModel<Provider>> getProviders() {
        return ResponseEntity.ok(this.userService.getProviders());
    }

    @DeleteMapping("/private/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userID") UUID userID) {
        this.userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
}
