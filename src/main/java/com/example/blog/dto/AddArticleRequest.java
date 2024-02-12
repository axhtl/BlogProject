package com.example.blog.dto;

import com.example.blog.domain.Article;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AddArticleRequest {

    @Pattern(regexp = ".{1,199}", message="title은 글자수 200 이상 불가")
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
