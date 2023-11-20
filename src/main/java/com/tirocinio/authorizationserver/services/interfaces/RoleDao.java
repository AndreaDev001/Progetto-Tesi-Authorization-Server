package com.tirocinio.authorizationserver.services.interfaces;

import com.tirocinio.authorizationserver.data.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface RoleDao
{
    CollectionModel<Role> getRoles();
    Role getRole(UUID roleID);
    void deleteRole(UUID roleID);
}
