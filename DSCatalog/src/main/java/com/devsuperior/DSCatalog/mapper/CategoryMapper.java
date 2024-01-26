package com.devsuperior.DSCatalog.mapper;

import com.devsuperior.DSCatalog.dto.CategoryDTO;
import com.devsuperior.DSCatalog.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

public class CategoryMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO categoryToCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category categoryDTOToCategory(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
