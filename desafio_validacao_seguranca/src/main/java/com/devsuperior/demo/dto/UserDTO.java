package com.devsuperior.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @Email(message = "Please enter a valid email")
    private String email;

    private String password;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(Long id, String email, Set<RoleDTO> roles) {
        this.id = id;
        this.email = email;
        roles.addAll(roles);
    }
}