package org.example.techblog.services.impl;

import org.example.techblog.dtos.articledtos.ArticleCreateDto;
import org.example.techblog.dtos.articledtos.ArticleDto;
import org.example.techblog.models.Article;
import org.example.techblog.models.Category;
import org.example.techblog.models.UserEntity;
import org.example.techblog.repositories.ArticleRepository;
import org.example.techblog.repositories.CategoryRepository;
import org.example.techblog.repositories.UserRepository;
import org.example.techblog.services.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
//    public void addArticle(ArticleCreateDto articleDto) {
//        Article article = modelMapper.map(articleDto, Article.class);
//        Category category = categoryRepository.findById(articleDto.getCategoryId()).get();
//        UserEntity user = userRepository.findById(9);
//        article.setUpdatedDate(new Date());
//        article.setCreatedDate(new Date());
//        article.setCategory(category);
//        article.setUser(user);
//        articleRepository.save(article);
//    }

    public void addArticle(ArticleCreateDto articleDto) {
        Article article = modelMapper.map(articleDto, Article.class);
        Category category = categoryRepository.findById(articleDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email);


        article.setCreatedDate(new Date());
        article.setUpdatedDate(new Date());

        article.setCategory(category);
        article.setUser(user);

        articleRepository.save(article);
    }

    @Override
    public List<ArticleDto> getArticles() {
        List<ArticleDto> articleDtoList = articleRepository.findAll().stream()
                .map(article -> modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());
        return articleDtoList;
    }
}
