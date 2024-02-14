package com.devsuperior.DSCatalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    @NotBlank(message = "Required field")
    @Email(message = "Please enter a valid email")
    private String email;
}
