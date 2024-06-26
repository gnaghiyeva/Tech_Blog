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
public class GadgetController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @GetMapping("/gadgets")
    public String gadgets(Model model){
        List<ArticleHomeDto> homeArticles = articleService.getHomeArticles();
        List<CategoryDto> homeCategories = categoryService.getAllCategories();
        model.addAttribute("articles", homeArticles);
        model.addAttribute("categories", homeCategories);
        return "gadgets";
    }
}
