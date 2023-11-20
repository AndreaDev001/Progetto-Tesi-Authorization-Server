package com.tirocinio.authorizationserver.controllers;


import com.tirocinio.authorizationserver.data.dto.input.PaginationRequest;
import com.tirocinio.authorizationserver.data.dto.output.RoleDto;
import com.tirocinio.authorizationserver.data.entities.Role;
import com.tirocinio.authorizationserver.services.interfaces.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/roles")
@RequiredArgsConstructor
public class RoleController
{
    private final RoleService roleService;

    @GetMapping("/private")
    public ResponseEntity<CollectionModel<RoleDto>> getRoles() {
        return ok(this.roleService.getRoles());
    }
    @GetMapping("/private/id/{roleID}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("roleID") UUID roleID) {
        return ok(this.roleService.getRole(roleID));
    }
    @GetMapping("/private/name/{roleName}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("roleName") String roleName) {
        return ResponseEntity.ok(this.roleService.getRole(roleName));
    }

    @PostMapping("/private/{roleName}")
    public ResponseEntity<RoleDto> createRole(@PathVariable("roleName") String roleName) {
        return ResponseEntity.status(201).body(this.roleService.createRole(roleName));
    }

    @DeleteMapping("/private/{roleID}")
    public ResponseEntity<RoleDto> deleteRole(@PathVariable("roleID") UUID roleID) {
        this.roleService.deleteRole(roleID);
        return ResponseEntity.noContent().build();
    }
}
