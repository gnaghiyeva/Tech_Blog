package org.example.techblog.repositories;

import org.example.techblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long id);
}
