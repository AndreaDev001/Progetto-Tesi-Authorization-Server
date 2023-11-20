package com.tirocinio.authorizationserver.services.interfaces;

import com.tirocinio.authorizationserver.data.dto.output.RoleDto;
import com.tirocinio.authorizationserver.data.entities.Role;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public interface RoleService
{
    CollectionModel<RoleDto> getRoles();
    RoleDto getRole(UUID roleID);
    RoleDto getRole(String name);
    RoleDto createRole(String name);
    void deleteRole(UUID roleID);
}
