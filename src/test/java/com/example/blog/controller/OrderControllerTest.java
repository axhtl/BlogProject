package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void MockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("내림차순 조회 테스트")
    @Test
    public void testGetDescendingOrder() throws Exception {
        final String url = "/api/descending";

        Article article1 = Article.builder()
                        .title("제목1")
                        .content("내용1")
                        .build();
        article1.setCreatedAt(LocalDateTime.now().minusDays(2));
        blogRepository.save(article1);

        Article article2 = Article.builder()
                .title("제목2")
                .content("내용2")
                .build();
        article2.setCreatedAt(LocalDateTime.now().minusDays(1));
        blogRepository.save(article2);

        Article article3 = Article.builder()
                .title("제목3")
                .content("내용3")
                .build();
        article3.setCreatedAt(LocalDateTime.now());
        blogRepository.save(article3);

        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("제목3"))
                .andExpect(jsonPath("$[1].title").value("제목2"))
                .andExpect(jsonPath("$[2].title").value("제목1"));
    }

    @Test
    public void testGetAscendingOrder() throws Exception {
        final String url = "/api/ascending";

        Article article1 = Article.builder()
                .title("제목1")
                .content("내용1")
                .build();
        article1.setCreatedAt(LocalDateTime.now().minusDays(2));
        blogRepository.save(article1);

        Article article2 = Article.builder()
                .title("제목2")
                .content("내용2")
                .build();
        article2.setCreatedAt(LocalDateTime.now().minusDays(1));
        blogRepository.save(article2);

        Article article3 = Article.builder()
                .title("제목3")
                .content("내용3")
                .build();
        article3.setCreatedAt(LocalDateTime.now());
        blogRepository.save(article3);

        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("제목1"))
                .andExpect(jsonPath("$[1].title").value("제목2"))
                .andExpect(jsonPath("$[2].title").value("제목3"));
    }
}
