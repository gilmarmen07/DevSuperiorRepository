package com.devsuperior.DSCatalog.services;

import com.devsuperior.DSCatalog.dto.UserDTO;
import com.devsuperior.DSCatalog.dto.UserInsertDTO;
import com.devsuperior.DSCatalog.dto.UserUpdateDTO;
import com.devsuperior.DSCatalog.entities.Role;
import com.devsuperior.DSCatalog.entities.User;
import com.devsuperior.DSCatalog.mapper.UserMapper;
import com.devsuperior.DSCatalog.projections.UserDetailsProjection;
import com.devsuperior.DSCatalog.repositories.RoleRepository;
import com.devsuperior.DSCatalog.repositories.UserRepository;
import com.devsuperior.DSCatalog.services.exceptions.DatabaseException;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> userMapper.userToUserDTO(user));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userMapper.userToUserDTO(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found")));
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userDTO) {
        User user = new User();
        userMapper.copyUserInsertDTOToUser(userDTO, user);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userDTO) {
        try {
            User user = userRepository.getReferenceById(id);
            userMapper.copyUserDTOToUser(userDTO, user);
            return userMapper.userToUserDTO(userRepository.save(user));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Register not found");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Register not found");
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email not found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    @Transactional(readOnly = true)
    public UserDTO findByMe() {
        return userMapper.userToUserDTO(authService.authenticated());
    }
}
