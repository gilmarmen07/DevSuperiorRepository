package com.devsuperior.clientcrud.configuration;

import com.devsuperior.clientcrud.mapper.ClientMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ClientMapper productMapper() {
        return new ClientMapper();
    }
}
