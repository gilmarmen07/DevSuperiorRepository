package com.devsuperior.demo.mapper;

import com.devsuperior.demo.dto.RoleDTO;
import com.devsuperior.demo.dto.UserDTO;
import com.devsuperior.demo.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleMapper roleMapper;

    public UserDTO userToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User userDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }


    public void copyUserDTOToUser(UserDTO userDTO, User user) {
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.getRoles().clear();
        for (RoleDTO dto : userDTO.getRoles()) {
            user.getRoles().add(roleMapper.roleDTOToRole(dto));
        }
    }
}
