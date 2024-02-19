package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/api/descending")
    public ResponseEntity<List<Article>> getDescendingOrder(){
        List<Article> AllDescending = orderService.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok().body(AllDescending);
    }

    @GetMapping("/api/ascending")
    public ResponseEntity<List<Article>> getAscendingOrder(){
        List<Article> AllAscending = orderService.findAllByOrderByCreatedAtAsc();
        return ResponseEntity.ok().body(AllAscending);
    }
}
