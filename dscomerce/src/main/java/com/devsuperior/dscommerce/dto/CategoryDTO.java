package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    Long id;
    String name;

    public CategoryDTO(Category category) {
        id = category.getId();
        name = category.getName();
    }
}
