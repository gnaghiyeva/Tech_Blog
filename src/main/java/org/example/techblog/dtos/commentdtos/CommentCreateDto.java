package org.example.techblog.dtos.commentdtos;

import lombok.Getter;
import lombok.Setter;
import org.example.techblog.models.Article;

import java.util.Date;

@Getter
@Setter
public class CommentCreateDto {
    private String authorName;
    private String authorEmail;
    private String content;
//    private Date createdDate;
    private Long articleId;
}
