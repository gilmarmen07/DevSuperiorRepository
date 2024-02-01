package com.devsuperior.DSCatalog.dto;

import com.devsuperior.DSCatalog.services.validation.UserInsertValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@UserInsertValid
public class UserInsertDTO extends UserDTO{

    private String password;
}
