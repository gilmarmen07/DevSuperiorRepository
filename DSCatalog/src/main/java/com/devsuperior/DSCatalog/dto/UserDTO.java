package com.devsuperior.DSCatalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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
    @NotBlank(message = "Required field")
    private String firstName;
    @NotBlank(message = "Required field")
    private String lastName;
    @Email(message = "Please enter a valid email")
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(Long id, String firstName, String lastName, String email, Set<RoleDTO> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
