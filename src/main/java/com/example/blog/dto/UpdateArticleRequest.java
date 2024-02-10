package com.example.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;

    @Builder
    public UpdateArticleRequest(String title, String content){
        this.title=title;
        this.content=content;
    }
}
