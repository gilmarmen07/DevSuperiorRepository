package com.devsuperior.DSCatalog.mapper;

import com.devsuperior.DSCatalog.dto.RoleDTO;
import com.devsuperior.DSCatalog.dto.UserDTO;
import com.devsuperior.DSCatalog.dto.UserInsertDTO;
import com.devsuperior.DSCatalog.entities.User;
import com.devsuperior.DSCatalog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UserDTO userToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User userDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }


    public void copyUserInsertDTOToUser(UserInsertDTO userDTO, User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.getRoles().clear();
        user.getRoles().add(roleRepository.findByAuthority("ROLE_OPERATOR"));
    }

    public void copyUserDTOToUser(UserDTO userDTO, User user) {
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.getRoles().clear();
        for (RoleDTO dto : userDTO.getRoles()) {
            user.getRoles().add(roleMapper.roleDTOToRole(dto));
        }
    }
}
