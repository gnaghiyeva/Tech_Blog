package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.services.ArticleService;
import org.example.techblog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class FooterController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @ModelAttribute("footerItems")
    public List<CategoryDto> getFooterItems() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ArticleDto> articles = articleService.getArticles();


        Map<Long, Long> articleCountByCategory = articles.stream()
                .collect(Collectors.groupingBy(article -> article.getCategory().getId(), Collectors.counting()));

        categories.forEach(category ->
                category.setArticleCount(articleCountByCategory.getOrDefault(category.getId(), 0L))
        );

        return categories;
    }

    @ModelAttribute("articleItems")
    public List<ArticleDto> getFooterArticles() {
        return  articleService.getArticles();
    }
}
