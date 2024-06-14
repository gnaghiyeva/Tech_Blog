package org.example.techblog.services;

import org.example.techblog.dtos.articledtos.ArticleCreateDto;
import org.example.techblog.dtos.articledtos.ArticleDto;
import org.example.techblog.dtos.articledtos.ArticleUpdateDto;

import java.util.List;

public interface ArticleService {
    void addArticle(ArticleCreateDto articleDto);

    List<ArticleDto> getArticles();

    void updateArticle(ArticleUpdateDto articleDto);

    ArticleUpdateDto findUpdatedArticle(Long id);
    ArticleDto getArticleById(Long id);
    void removeArticle(Long articleId);
}
