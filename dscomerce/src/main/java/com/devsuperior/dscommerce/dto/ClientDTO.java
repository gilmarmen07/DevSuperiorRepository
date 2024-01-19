package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;

    public ClientDTO(User user) {
        id = user.getId();
        name = user.getName();
    }
}
