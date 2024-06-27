package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleHomeDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.categorydtos.CategoryHomeDto;
import org.example.techblog.services.ArticleService;
import org.example.techblog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService; // 123

    @GetMapping("/")
    public String index(Model model) {
        List<ArticleHomeDto> homeArticles = articleService.getHomeArticles();
        List<CategoryDto> homeCategories = categoryService.getAllCategories();
        List<ArticleHomeDto> mostViews = articleService.getMostView();
        model.addAttribute("articles", homeArticles);
        model.addAttribute("categories", homeCategories);
        model.addAttribute("mostViews", mostViews);
        return "home";
    }


}
