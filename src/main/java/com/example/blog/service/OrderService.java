package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final BlogRepository blogRepository;

    public List<Article> findAllByOrderByCreatedAtDesc(){
        return blogRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Article> findAllByOrderByCreatedAtAsc(){
        return blogRepository.findAllByOrderByCreatedAtAsc();
    }
}
