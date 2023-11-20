package com.tirocinio.authorizationserver.services.implementations;

import com.tirocinio.authorizationserver.data.dao.LocalUserDao;
import com.tirocinio.authorizationserver.data.dto.input.CreateLocalUserDto;
import com.tirocinio.authorizationserver.data.dto.output.LocalUserDto;
import com.tirocinio.authorizationserver.data.entities.LocalUser;
import com.tirocinio.authorizationserver.services.interfaces.LocalUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LocalUserServiceImp extends GenericServiceImp<LocalUser, LocalUserDto> implements LocalUserService
{
    private final LocalUserDao localUserDao;
    public LocalUserServiceImp(LocalUserDao localUserDao,ModelMapper modelMapper, PagedResourcesAssembler<LocalUser> pagedResourcesAssembler) {
        super(modelMapper, LocalUser.class,LocalUserDto.class, pagedResourcesAssembler);
        this.localUserDao = localUserDao;
    }
    @Override
    public PagedModel<LocalUserDto> getUsers(Pageable pageable) {
        Page<LocalUser> localUsers = this.localUserDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(localUsers,modelAssembler);
    }
    @Override
    public PagedModel<LocalUserDto> getUsers(String name, String surname, Pageable pageable) {
        Page<LocalUser> localUsers = this.localUserDao.getUsers(name,surname,pageable);
        return this.pagedResourcesAssembler.toModel(localUsers,modelAssembler);
    }

    @Override
    public LocalUserDto createUser(CreateLocalUserDto createLocalUserDto) {
        return null;
    }

    @Override
    public LocalUserDto getUser(UUID id) {
        LocalUser localUser = this.localUserDao.findById(id).orElseThrow();
        return this.modelMapper.map(localUser,LocalUserDto.class);
    }
}
