package com.tirocinio.authorizationserver.services.interfaces;

import com.tirocinio.authorizationserver.data.dto.output.UserDto;
import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface UserService
{
    PagedModel<UserDto> getUsers(Pageable pageable);
    PagedModel<UserDto> getUsers(Provider provider,Pageable pageable);
    UserDto getUser(UUID id);
    CollectionModel<Provider> getProviders();
    void deleteUser(UUID id);
}
