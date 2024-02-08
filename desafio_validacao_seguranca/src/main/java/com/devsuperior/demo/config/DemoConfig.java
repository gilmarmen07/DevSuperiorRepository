package com.devsuperior.demo.config;

import com.devsuperior.demo.mapper.EventMapper;
import com.devsuperior.demo.mapper.RoleMapper;
import com.devsuperior.demo.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DemoConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public RoleMapper roleMapper() {
        return new RoleMapper();
    }

    @Bean
    public EventMapper eventMapper() {
        return new EventMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}