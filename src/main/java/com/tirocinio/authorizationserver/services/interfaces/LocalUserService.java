package com.tirocinio.authorizationserver.services.interfaces;

import com.tirocinio.authorizationserver.data.dto.input.CreateLocalUserDto;
import com.tirocinio.authorizationserver.data.dto.output.LocalUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface LocalUserService
{
    PagedModel<LocalUserDto> getUsers(Pageable pageable);
    PagedModel<LocalUserDto> getUsers(String name,String surname,Pageable pageable);
    LocalUserDto createUser(CreateLocalUserDto createLocalUserDto);
    LocalUserDto getUser(UUID id);
}
