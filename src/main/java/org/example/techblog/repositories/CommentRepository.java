package org.example.techblog.repositories;

import org.example.techblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
    // Gerektiğinde özel sorgular buraya eklenebilir
}
