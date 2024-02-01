package com.devsuperior.DSCatalog.services;

import com.devsuperior.DSCatalog.dto.RoleDTO;
import com.devsuperior.DSCatalog.entities.Role;
import com.devsuperior.DSCatalog.mapper.RoleMapper;
import com.devsuperior.DSCatalog.repositories.RoleRepository;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public Page<RoleDTO> findAllPageable(Pageable pageable) {
        return roleRepository.findAll(pageable).map(role -> roleMapper.roleToRoleDTO(role));
    }

    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        return roleMapper.roleToRoleDTO(roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found")));
    }

    @Transactional
    public RoleDTO insert(RoleDTO roleDTO) {
        return roleMapper.roleToRoleDTO(roleRepository.save(roleMapper.roleDTOToRole(roleDTO)));
    }

    @Transactional
    public RoleDTO update(Long id, RoleDTO roleDTO) {
        try {
            Role role = roleRepository.getReferenceById(id);
            role.setAuthority(roleDTO.getAuthority());
            return roleMapper.roleToRoleDTO(roleRepository.save(role));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Register not found");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Register not found");
        }
        roleRepository.deleteById(id);
    }
}
