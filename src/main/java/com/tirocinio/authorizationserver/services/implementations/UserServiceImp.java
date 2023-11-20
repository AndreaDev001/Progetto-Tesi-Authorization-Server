package com.tirocinio.authorizationserver.services.implementations;

import com.tirocinio.authorizationserver.data.dao.UserDao;
import com.tirocinio.authorizationserver.data.dto.output.GenericOutput;
import com.tirocinio.authorizationserver.data.dto.output.UserDto;
import com.tirocinio.authorizationserver.data.entities.User;
import com.tirocinio.authorizationserver.data.entities.enums.Provider;
import com.tirocinio.authorizationserver.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImp extends GenericServiceImp<User,UserDto> implements UserService
{
    private final UserDao userDao;

    public UserServiceImp(UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<User> pagedResourcesAssembler) {
        super(modelMapper, User.class,UserDto.class, pagedResourcesAssembler);
        this.userDao = userDao;
    }

    @Override
    public PagedModel<UserDto> getUsers(Pageable pageable) {
        Page<User> users = this.userDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDto> getUsers(Provider provider, Pageable pageable) {
        Page<User> users = this.userDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public UserDto getUser(UUID id) {
        User user = this.userDao.findById(id).orElseThrow();
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public void deleteUser(UUID id) {
        this.userDao.findById(id).orElseThrow();
        this.userDao.deleteById(id);
    }
}
