package org.example.techblog.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authorName;
    private String authorEmail;
    private String content;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
