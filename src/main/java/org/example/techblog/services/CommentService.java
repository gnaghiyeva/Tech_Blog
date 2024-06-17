package org.example.techblog.services;

import org.example.techblog.dtos.commentdtos.CommentCreateDto;
import org.example.techblog.dtos.commentdtos.CommentDto;

import java.util.List;

public interface CommentService {
    void addComment(CommentCreateDto commentDto);

    List<CommentDto> getCommentsByArticleId(Long articleId);
}
