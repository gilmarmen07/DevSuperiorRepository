package com.devsuperior.DSCatalog.services;

import com.devsuperior.DSCatalog.dto.CategoryDTO;
import com.devsuperior.DSCatalog.entities.Category;
import com.devsuperior.DSCatalog.mapper.CategoryMapper;
import com.devsuperior.DSCatalog.repositories.CategoryRepository;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPageable(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(category -> categoryMapper.categoryToCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return categoryMapper.categoryToCategoryDTO(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found")));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        return categoryMapper.categoryToCategoryDTO(categoryRepository.save(categoryMapper.categoryDTOToCategory(categoryDTO)));
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(categoryDTO.getName());
            return categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Register not found");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Register not found");
        }
        categoryRepository.deleteById(id);
    }
}
