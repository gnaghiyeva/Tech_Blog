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

    @Value("${image.upload-dir}")
    private String uploadDirImage;

    @Value("${video.upload-dir}")
    private String uploadDirVideo;

    private Path getSourceUploadDirectoryImage() {
        return Paths.get("src/main/resources/static/uploads");
    }

    private Path getTargetUploadDirectoryImage() {
        return Paths.get("target/classes/static/uploads");
    }

    private Path getSourceUploadDirectoryVideo() {
        return Paths.get("src/main/resources/static/videos");
    }

    private Path getTargetUploadDirectoryVideo() {
        return Paths.get("target/classes/static/videos");
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
    public String articleCreate(@ModelAttribute ArticleCreateDto articleDto,
                                @RequestParam("image") MultipartFile image,
                                @RequestParam(value = "video", required = false) MultipartFile video) throws IOException {

        Path sourceUploadDirectoryImage = getSourceUploadDirectoryImage();
        Path targetUploadDirectoryImage = getTargetUploadDirectoryImage();

        Path sourceUploadDirectoryVideo = getSourceUploadDirectoryVideo();
        Path targetUploadDirectoryVideo = getTargetUploadDirectoryVideo();

        if (!Files.exists(sourceUploadDirectoryImage)) {
            Files.createDirectories(sourceUploadDirectoryImage);
        }

        if (!Files.exists(targetUploadDirectoryImage)) {
            Files.createDirectories(targetUploadDirectoryImage);
        }

        if (!Files.exists(sourceUploadDirectoryVideo)) {
            Files.createDirectories(sourceUploadDirectoryVideo);
        }

        if (!Files.exists(targetUploadDirectoryVideo)) {
            Files.createDirectories(targetUploadDirectoryVideo);
        }

        UUID rand = UUID.randomUUID();

        String imageFilename = rand + image.getOriginalFilename();
        String videoFilename = null; // Initialize videoFilename as null initially

        if (video != null && !video.isEmpty()) {
            videoFilename = rand + video.getOriginalFilename();
            articleDto.setVideoUrl("/videos/" + videoFilename);

            // Save to source directory
            Files.copy(video.getInputStream(), sourceUploadDirectoryVideo.resolve(videoFilename));

            // Save to target directory
            Files.copy(video.getInputStream(), targetUploadDirectoryVideo.resolve(videoFilename));
        } else {
            articleDto.setVideoUrl(null); // Set videoUrl to null if video is empty
        }

        articleDto.setPhotoUrl("/uploads/" + imageFilename);

        // Save image to source and target directories
        Files.copy(image.getInputStream(), sourceUploadDirectoryImage.resolve(imageFilename));
        Files.copy(image.getInputStream(), targetUploadDirectoryImage.resolve(imageFilename));

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
                                @RequestParam("image") MultipartFile image,
                                @RequestParam("video") MultipartFile video) throws IOException {

        Path sourceUploadDirectoryImage = getSourceUploadDirectoryImage();
        Path targetUploadDirectoryImage = getTargetUploadDirectoryImage();
        Path sourceUploadDirectoryVideo = getSourceUploadDirectoryVideo();
        Path targetUploadDirectoryVideo = getTargetUploadDirectoryVideo();

        // Ensure directories exist
        if (!Files.exists(sourceUploadDirectoryImage)) {
            Files.createDirectories(sourceUploadDirectoryImage);
        }
        if (!Files.exists(targetUploadDirectoryImage)) {
            Files.createDirectories(targetUploadDirectoryImage);
        }
        if (!Files.exists(sourceUploadDirectoryVideo)) {
            Files.createDirectories(sourceUploadDirectoryVideo);
        }
        if (!Files.exists(targetUploadDirectoryVideo)) {
            Files.createDirectories(targetUploadDirectoryVideo);
        }

        ArticleDto existingArticle = articleService.getArticleById(articleDto.getId());

        if (!image.isEmpty()) {
            // Handle image update
            UUID rand = UUID.randomUUID();
            String imageFilename = rand + image.getOriginalFilename();

            // Delete existing image if present
            if (existingArticle != null && existingArticle.getPhotoUrl() != null) {
                Path previousImagePathSource = sourceUploadDirectoryImage.resolve(existingArticle.getPhotoUrl().substring(existingArticle.getPhotoUrl().lastIndexOf("/") + 1));
                Path previousImagePathTarget = targetUploadDirectoryImage.resolve(existingArticle.getPhotoUrl().substring(existingArticle.getPhotoUrl().lastIndexOf("/") + 1));
                Files.deleteIfExists(previousImagePathSource);
                Files.deleteIfExists(previousImagePathTarget);
            }

            // Set new image URL
            articleDto.setPhotoUrl("/uploads/" + imageFilename);

            // Save new image file
            Files.copy(image.getInputStream(), sourceUploadDirectoryImage.resolve(imageFilename));
            Files.copy(image.getInputStream(), targetUploadDirectoryImage.resolve(imageFilename));
        } else {
            // Keep the existing image URL if not updated
            if (existingArticle != null) {
                articleDto.setPhotoUrl(existingArticle.getPhotoUrl());
            }
        }

        if (!video.isEmpty()) {
            // Handle video update
            UUID rand = UUID.randomUUID();
            String videoFilename = rand + video.getOriginalFilename();

            // Delete existing video if present
            if (existingArticle != null && existingArticle.getVideoUrl() != null) {
                Path previousVideoPathSource = sourceUploadDirectoryVideo.resolve(existingArticle.getVideoUrl().substring(existingArticle.getVideoUrl().lastIndexOf("/") + 1));
                Path previousVideoPathTarget = targetUploadDirectoryVideo.resolve(existingArticle.getVideoUrl().substring(existingArticle.getVideoUrl().lastIndexOf("/") + 1));
                Files.deleteIfExists(previousVideoPathSource);
                Files.deleteIfExists(previousVideoPathTarget);
            }

            // Set new video URL
            articleDto.setVideoUrl("/videos/" + videoFilename);

            // Save new video file
            Files.copy(video.getInputStream(), sourceUploadDirectoryVideo.resolve(videoFilename));
            Files.copy(video.getInputStream(), targetUploadDirectoryVideo.resolve(videoFilename));
        } else {
            // Keep the existing video URL if not updated
            if (existingArticle != null) {
                articleDto.setVideoUrl(existingArticle.getVideoUrl());
            }
        }

        articleService.updateArticle(articleDto);
        return "redirect:/admin/article";
    }    @GetMapping("/admin/article/remove/{id}")
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
