package com.example.blog.dto;

import com.example.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    @Builder
    public AddArticleRequest(String title, String content){
        this.title=title;
        this.content=content;
    }

    public Article toEntity(){ //dto를 엔티티로 만들어주는 메서드
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
