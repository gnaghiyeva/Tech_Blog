package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleHomeDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.services.ArticleService;
import org.example.techblog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/reviews")
    public String reviews(Model model){
        List<ArticleHomeDto> homeArticles = articleService.getHomeArticles();
        List<CategoryDto> homeCategories = categoryService.getAllCategories();
        List<ArticleHomeDto> mostViews = articleService.getMostView();
        List<ArticleHomeDto> mostViewVideos = articleService.getMostViewVideo();
        List<ArticleHomeDto> recentViewArticles = articleService.recentViewedArticles();
        model.addAttribute("articles", homeArticles);
        model.addAttribute("categories", homeCategories);
        model.addAttribute("mostViews", mostViews);
        model.addAttribute("mostViewVideos", mostViewVideos);
        model.addAttribute("recentViewArticles", recentViewArticles);
        return "reviews";
    }
}
