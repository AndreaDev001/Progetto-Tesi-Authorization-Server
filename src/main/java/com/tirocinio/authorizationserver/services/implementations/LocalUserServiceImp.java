package com.tirocinio.authorizationserver.services.implementations;

import com.tirocinio.authorizationserver.data.dao.LocalUserDao;
import com.tirocinio.authorizationserver.data.dto.input.CreateLocalUserDto;
import com.tirocinio.authorizationserver.data.dto.output.LocalUserDto;
import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import com.tirocinio.authorizationserver.data.entities.users.LocalUser;
import com.tirocinio.authorizationserver.services.interfaces.LocalUserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LocalUserServiceImp extends GenericServiceImp<LocalUser, LocalUserDto> implements LocalUserService
{
    private final LocalUserDao localUserDao;
    private final PasswordEncoder passwordEncoder;

    public LocalUserServiceImp(PasswordEncoder passwordEncoder,LocalUserDao localUserDao,ModelMapper modelMapper, PagedResourcesAssembler<LocalUser> pagedResourcesAssembler) {
        super(modelMapper, LocalUser.class,LocalUserDto.class, pagedResourcesAssembler);
        this.localUserDao = localUserDao;
        this.passwordEncoder = passwordEncoder;
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
    @Transactional
    public LocalUserDto createUser(CreateLocalUserDto createLocalUserDto) {
        LocalUser localUser = new LocalUser();
        localUser.setEmail(createLocalUserDto.getEmail());
        localUser.setName(createLocalUserDto.getName());
        localUser.setSurname(createLocalUserDto.getSurname());
        localUser.setUsername(createLocalUserDto.getSurname());
        localUser.setPassword(passwordEncoder.encode(createLocalUserDto.getPassword()));
        localUser.setProvider(Provider.LOCAL);
        localUser = this.localUserDao.save(localUser);
        return this.modelMapper.map(localUser,LocalUserDto.class);
    }

    @Override
    public LocalUserDto getUser(UUID id) {
        LocalUser localUser = this.localUserDao.findById(id).orElseThrow();
        return this.modelMapper.map(localUser,LocalUserDto.class);
    }
}
