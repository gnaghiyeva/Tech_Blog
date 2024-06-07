package org.example.techblog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Configure {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
