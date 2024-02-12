package com.example.blog.entity;

import com.example.blog.domain.Article;
import com.example.blog.repository.BlogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseEntityTest {

    @Autowired
    BlogRepository blogRepository;

    @Test
    public void auditingTest() {
        Article article = new Article();
        article.setTitle("title");
        article.setContent("content");

        Article savedArticle = blogRepository.save(article);

        System.out.println("Title : " + savedArticle.getTitle());
        System.out.println("createdAt : " + savedArticle.getCreatedAt());

        article.update("newTitle","newConent");
        System.out.println("Title : " + savedArticle.getTitle());
        System.out.println("updatedAt : " + savedArticle.getUpdatedAt());

    }
}

