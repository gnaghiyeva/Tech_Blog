package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleCreateDto;
import org.example.techblog.dtos.articledtos.ArticleDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.userdtos.UserDto;
import org.example.techblog.services.ArticleService;
import org.example.techblog.services.CategoryService;
import org.example.techblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class ArticleController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/article")
    public String articleGet(Model model)
    {
        List<ArticleDto> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        return "/dashboard/article/article";
    }

    @GetMapping("/admin/article/article-create")
    public String articleCreate(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/dashboard/article/article-create";
    }

    @PostMapping("/admin/article/create")
    public String articleCreate(@ModelAttribute ArticleCreateDto articleDto) {
        articleService.addArticle(articleDto);
        return "redirect:/admin/article";
    }

}
