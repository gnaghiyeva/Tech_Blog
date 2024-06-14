package org.example.techblog.repositories;

import org.example.techblog.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findByCategoryId(Long categoryId);
}
