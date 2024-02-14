package com.devsuperior.DSCatalog.dto;

import com.devsuperior.DSCatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@UserInsertValid
public class UserInsertDTO extends UserDTO{

    @NotBlank(message = "Required field")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
}
