package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.dto.SearchRequest;
import com.example.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BlogRepository blogRepository;

    public List<SearchRequest> searchArticles(String keyword){
        List<Article> articles = blogRepository.findByTitleContaining(keyword);
        return articles.stream() // 엔티티 객체들로 이루어진 Stream 생성 -> 각 객체에 대해 연속적으로 연산 수행 가능
                .map(this::convertToDTO) // 엔티티 객체를 DTO로 변환
                .collect(Collectors.toList()); // 변환된 DTO들을 리스트로 수집하여 반환
    }

    private SearchRequest convertToDTO(Article article){
        SearchRequest dto = new SearchRequest();
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        return dto;
    }
}
