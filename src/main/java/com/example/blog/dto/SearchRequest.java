package com.example.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRequest {
    private String title;
    private String content;

}
