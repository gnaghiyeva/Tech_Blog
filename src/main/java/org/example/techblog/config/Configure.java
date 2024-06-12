package org.example.techblog.config;

import org.example.techblog.dtos.articledtos.ArticleCreateDto;
import org.example.techblog.models.Article;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Configure {
//    @Bean
//    public ModelMapper modelMapper(){
//        return new ModelMapper();
//    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // ArticleCreateDto to Article mapping
        modelMapper.addMappings(new PropertyMap<ArticleCreateDto, Article>() {
            @Override
            protected void configure() {
                skip(destination.getId()); // ID alanını atla
                skip(destination.getCategory()); // Category alanını atla
                skip(destination.getUser()); // User alanını atla
            }
        });

        return modelMapper;
    }
}
