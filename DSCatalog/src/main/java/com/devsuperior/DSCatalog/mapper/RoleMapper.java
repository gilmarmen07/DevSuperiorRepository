package com.devsuperior.DSCatalog.mapper;

import com.devsuperior.DSCatalog.dto.RoleDTO;
import com.devsuperior.DSCatalog.dto.RoleDTO;
import com.devsuperior.DSCatalog.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleMapper {
    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO roleToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role roleDTOToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }
}
