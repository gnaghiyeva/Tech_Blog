package org.example.techblog.services;

import org.example.techblog.dtos.articledtos.ArticleCreateDto;
import org.example.techblog.dtos.articledtos.ArticleDto;

import java.util.List;

public interface ArticleService {
    void addArticle(ArticleCreateDto articleDto);
    List<ArticleDto> getArticles();
}
