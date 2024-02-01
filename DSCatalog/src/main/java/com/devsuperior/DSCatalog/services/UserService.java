package com.devsuperior.DSCatalog.services;

import com.devsuperior.DSCatalog.dto.UserDTO;
import com.devsuperior.DSCatalog.dto.UserInsertDTO;
import com.devsuperior.DSCatalog.dto.UserUpdateDTO;
import com.devsuperior.DSCatalog.entities.User;
import com.devsuperior.DSCatalog.mapper.UserMapper;
import com.devsuperior.DSCatalog.repositories.UserRepository;
import com.devsuperior.DSCatalog.services.exceptions.DatabaseException;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        User user = userRepository.save(userMapper.userDTOToUser(userDTO));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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
}
