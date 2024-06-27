package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.services.ArticleService;
import org.example.techblog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class FooterController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @ModelAttribute("footerItems")
    public List<CategoryDto> getFooterItems() {
        return  categoryService.getAllCategories();

    }

    @ModelAttribute("articleItems")
    public List<ArticleDto> getFooterArticles() {
        return  articleService.getArticles();
    }
}
