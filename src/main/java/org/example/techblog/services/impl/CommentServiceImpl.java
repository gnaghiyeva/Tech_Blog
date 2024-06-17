package org.example.techblog.services.impl;

import org.example.techblog.dtos.commentdtos.CommentCreateDto;
import org.example.techblog.dtos.commentdtos.CommentDto;
import org.example.techblog.models.Article;
import org.example.techblog.models.Category;
import org.example.techblog.models.Comment;
import org.example.techblog.repositories.ArticleRepository;
import org.example.techblog.repositories.CommentRepository;
import org.example.techblog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void addComment(CommentCreateDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        Article article = articleRepository.findById(commentDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        comment.setCreatedDate(new Date());
        comment.setContent(commentDto.getContent());
        comment.setAuthorEmail(commentDto.getAuthorEmail());
        comment.setAuthorName(commentDto.getAuthorName());
        comment.setArticle(article);

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getCommentsByArticleId(Long articleId) {
        return List.of();
    }
}
