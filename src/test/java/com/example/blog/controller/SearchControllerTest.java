package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; //직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void MockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("title 검색 테스트")
    @Test
    public void title_search_test() throws Exception {
        //given
        final String url = "/api/search";

        blogRepository.save(Article.builder()
                .title("제목ㅁ")
                .content("내용1")
                .build());

        blogRepository.save(Article.builder()
                .title("제목ㄴ")
                .content("내용2")
                .build());

        blogRepository.save(Article.builder()
                .title("ㄹ")
                .content("내용3")
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                        .param("keyword","제목")
                        .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("제목ㅁ"))
                .andExpect(jsonPath("$[1].title").value("제목ㄴ"))
                .andExpect(jsonPath("$[2]").doesNotExist());
    }
}
