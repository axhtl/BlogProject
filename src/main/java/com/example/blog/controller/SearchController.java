package com.example.blog.controller;

import com.example.blog.dto.SearchRequest;
import com.example.blog.service.BlogService;
import com.example.blog.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    public ResponseEntity<List<SearchRequest>> searchArticles(@RequestParam String keyword){
        List<SearchRequest> searchedArticles = searchService.searchArticles(keyword);
        return ResponseEntity.ok(searchedArticles);
    }
}
