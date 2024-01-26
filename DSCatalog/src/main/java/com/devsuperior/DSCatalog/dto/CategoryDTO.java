package com.devsuperior.DSCatalog.dto;

import com.devsuperior.DSCatalog.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(Category category) {
        id = category.getId();
        name = category.getName();
    }
}
