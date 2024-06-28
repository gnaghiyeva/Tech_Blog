package org.example.techblog.services;

import org.example.techblog.dtos.articledtos.*;

import java.util.List;

public interface ArticleService {
    void addArticle(ArticleCreateDto articleDto);

    List<ArticleDto> getArticles();

    void updateArticle(ArticleUpdateDto articleDto);

    ArticleUpdateDto findUpdatedArticle(Long id);
    ArticleDto getArticleById(Long id);
    void removeArticle(Long articleId);
    List<ArticleHomeDto> getHomeArticles();

    List<ArticleHomeDto> getMostView();

    ArticleDetailDto articleDetail(Long id);

    List<ArticleHomeDto> getMostViewVideo();

    List<ArticleHomeDto> recentViewedArticles();

}
