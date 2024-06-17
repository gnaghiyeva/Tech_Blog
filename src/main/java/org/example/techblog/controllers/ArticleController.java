package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleCreateDto;
import org.example.techblog.dtos.articledtos.ArticleDetailDto;
import org.example.techblog.dtos.articledtos.ArticleDto;
import org.example.techblog.dtos.articledtos.ArticleUpdateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.userdtos.UserDto;
import org.example.techblog.services.ArticleService;
import org.example.techblog.services.CategoryService;
import org.example.techblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path getSourceUploadDirectory() {
        return Paths.get("src/main/resources/static/uploads");
    }

    private Path getTargetUploadDirectory() {
        return Paths.get("target/classes/static/uploads");
    }

//    final String uploadLocation = getClass().getClassLoader().getResource("static/uploads").toString();
//    final Path uploadDirectory = Paths.get(uploadLocation.substring(8, uploadLocation.length()) );

//    final String uploadLocation = "src/main/resources/static/uploads";
//    final Path uploadDirectory = Paths.get(uploadLocation);

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

        Path sourceUploadDirectory = getSourceUploadDirectory();
        Path targetUploadDirectory = getTargetUploadDirectory();

        if (!Files.exists(sourceUploadDirectory)) {
            Files.createDirectories(sourceUploadDirectory);
        }

        if (!Files.exists(targetUploadDirectory)) {
            Files.createDirectories(targetUploadDirectory);
        }

        UUID rand = UUID.randomUUID();
        String filename = rand + image.getOriginalFilename();
        articleDto.setPhotoUrl("/uploads/" + filename);

        // Save to source directory
        Files.copy(image.getInputStream(), sourceUploadDirectory.resolve(filename));
        // Save to target directory
        Files.copy(image.getInputStream(), targetUploadDirectory.resolve(filename));

        articleService.addArticle(articleDto);
        return "redirect:/admin/article";
    }


    @GetMapping("/admin/article/update/{id}")
    public String updateArticle(@ModelAttribute @PathVariable Long id, Model model){
        ArticleUpdateDto articleUpdateDto = articleService.findUpdatedArticle(id);
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("article", articleUpdateDto);
        return "dashboard/article/update";
    }

    @PostMapping("/admin/article/update")
    public String updateArticle(@ModelAttribute ArticleUpdateDto articleDto,
                                @RequestParam("image") MultipartFile image) throws IOException {

        if (!image.isEmpty()) {

            Path sourceUploadDirectory = getSourceUploadDirectory();
            Path targetUploadDirectory = getTargetUploadDirectory();

            if (!Files.exists(sourceUploadDirectory)) {
                Files.createDirectories(sourceUploadDirectory);
            }

            if (!Files.exists(targetUploadDirectory)) {
                Files.createDirectories(targetUploadDirectory);
            }

            UUID rand = UUID.randomUUID();
            String filename = rand + image.getOriginalFilename();
            articleDto.setPhotoUrl("/uploads/" + filename);

            Files.copy(image.getInputStream(), sourceUploadDirectory.resolve(filename));
            Files.copy(image.getInputStream(), targetUploadDirectory.resolve(filename));
        } else {
            ArticleDto existingArticle = articleService.getArticleById(articleDto.getId());
            if (existingArticle != null) {
                articleDto.setPhotoUrl(existingArticle.getPhotoUrl());
            }
        }

        articleService.updateArticle(articleDto);

        return "redirect:/admin/article";
    }

    @GetMapping("/admin/article/remove/{id}")
    public String removeArticle(@ModelAttribute @PathVariable Long id){
        articleService.removeArticle(id);
        return "redirect:/admin/article";
    }

    @GetMapping("detail/{id}/{seoUrl}")
    public String detail(@PathVariable Long id, Model model)
    {
        ArticleDetailDto articleDetail = articleService.articleDetail(id);
        model.addAttribute("article",articleDetail);
//        return "dashboard/article/detail";
        return "dashboard/article/detail";
    }


}
