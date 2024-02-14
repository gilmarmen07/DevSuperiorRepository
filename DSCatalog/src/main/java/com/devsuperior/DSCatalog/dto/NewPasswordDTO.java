package com.devsuperior.DSCatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordDTO {

    @NotBlank(message = "Required field")
    private String token;

    @NotBlank(message = "Required field")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
}
