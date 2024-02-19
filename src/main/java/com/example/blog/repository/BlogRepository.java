package com.example.blog.repository;

import com.example.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContaining(String keyword);

    @Query("SELECT a FROM Article a WHERE a.deletedAt IS NOT NULL")
    List<Article> findDeletedArticles();

    List<Article> findAllByOrderByCreatedAtDesc();

    List<Article> findAllByOrderByCreatedAtAsc();
}
