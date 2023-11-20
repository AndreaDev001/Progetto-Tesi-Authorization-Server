package com.tirocinio.authorizationserver.services.implementations;


import com.tirocinio.authorizationserver.data.dao.RoleDao;
import com.tirocinio.authorizationserver.data.dto.output.RoleDto;
import com.tirocinio.authorizationserver.data.entities.Role;
import com.tirocinio.authorizationserver.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp extends GenericServiceImp<Role,RoleDto> implements RoleService
{
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao,ModelMapper modelMapper,PagedResourcesAssembler<Role> pagedResourcesAssembler) {
        super(modelMapper, Role.class,RoleDto.class, pagedResourcesAssembler);
        this.roleDao = roleDao;
    }

    @Override
    public CollectionModel<RoleDto> getRoles() {
        List<Role> roles = this.roleDao.findAll();
        return CollectionModel.of(roles.stream().map(role -> this.modelMapper.map(role,RoleDto.class)).collect(Collectors.toList()));
    }

    @Override
    public RoleDto getRole(UUID roleID) {
        Role role = this.roleDao.findById(roleID).orElseThrow();
        return this.modelMapper.map(role,RoleDto.class);
    }

    @Override
    public void createRole(String name) {

    }

    @Override
    public void deleteRole(UUID roleID) {
        this.roleDao.findById(roleID).orElseThrow();
        this.roleDao.deleteById(roleID);
    }
}
