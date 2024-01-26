package com.devsuperior.DSCatalog.config;

import com.devsuperior.DSCatalog.mapper.CategoryMapper;
import com.devsuperior.DSCatalog.mapper.ProductMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DSCatalogConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }
}
