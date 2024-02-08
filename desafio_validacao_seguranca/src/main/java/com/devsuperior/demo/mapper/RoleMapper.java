package com.devsuperior.demo.mapper;

import com.devsuperior.demo.dto.RoleDTO;
import com.devsuperior.demo.entities.Role;
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
