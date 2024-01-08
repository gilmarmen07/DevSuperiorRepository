package com.devsuperior.dscommerce.configuration;

import com.devsuperior.dscommerce.mapper.ProductMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DSCommerceConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }
}
