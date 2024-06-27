package org.example.techblog.repositories;

import org.example.techblog.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findByCategoryId(Long categoryId);
}
