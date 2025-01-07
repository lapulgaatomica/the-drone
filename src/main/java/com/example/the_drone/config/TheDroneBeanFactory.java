package com.example.the_drone.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TheDroneBeanFactory {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
