package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleDetailDto;
import org.example.techblog.dtos.articledtos.ArticleDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.commentdtos.CommentCreateDto;
import org.example.techblog.dtos.userdtos.UserDto;
import org.example.techblog.services.ArticleService;
import org.example.techblog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class CommentController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;


//    @GetMapping("/admin/comment/comment-create")
//    public String commentCreate(Model model) {
//        List<ArticleDto> articles = articleService.getArticles();
//        model.addAttribute("articles", articles);
//
//        return "dashboard/article/detail";
//    }

    @PostMapping("/comment/create")
    public String commentCreate(@ModelAttribute CommentCreateDto commentDto, Model model) {
        ArticleDetailDto articleDetail = articleService.articleDetail(commentDto.getArticleId());
        model.addAttribute("article",articleDetail);
        commentService.addComment(commentDto);
        return "dashboard/article/detail";
    }


}
