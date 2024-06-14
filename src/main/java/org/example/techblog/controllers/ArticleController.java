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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class ArticleController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    final String uploadLocation = getClass().getClassLoader().getResource("static/uploads").toString();
    final Path uploadDirectory = Paths.get(uploadLocation.substring(6, uploadLocation.length()) );


    @GetMapping("/admin/article")
    public String articleGet(Model model) {
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
    public String articleCreate(@ModelAttribute ArticleCreateDto articleDto, @RequestParam("image") MultipartFile image) throws IOException {
        // src/main/resources/static/uploads dizinine erişim sağlıyoruz
//        String uploadLocation = "src/main/resources/static/uploads";
//        Path uploadDirectory = Paths.get(uploadLocation);

        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }

        UUID rand = UUID.randomUUID();
        String filename = rand + image.getOriginalFilename();
        articleDto.setPhotoUrl("/uploads/" + filename);

        Files.copy(image.getInputStream(), uploadDirectory.resolve(filename));
        articleService.addArticle(articleDto);
        return "redirect:/admin/article";
    }
}
