package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.dto.AddArticleRequest;
import com.example.blog.dto.UpdateArticleRequest;
import com.example.blog.repository.BlogRepository;
import com.example.blog.service.BlogService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void MockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }


    @DisplayName("save 메서드 테스트: 게시글 생성. DTO를 엔티티로 변환하여 DB에 저장")
    @Test
    public void save_Test() throws Exception {
        //given
        AddArticleRequest request = AddArticleRequest.builder()
                .title("제목")
                .content("내용")
                .build();

        //when
        Article result = blogService.save(request);

        //then
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getContent(), result.getContent());
    }


    @DisplayName("findAll 메서드 테스트: DB에서 전체 게시글 데이터 가져오기")
    @Test
    public void findAll_Test() throws Exception {
        //given
        Article a = new Article("제목 1", "내용 1");
        Article b = new Article("제목 2", "내용 2");
        Article c = new Article("제목 3", "내용 3");
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c));

        //when
        List<Article> result = blogService.findAll();

        //then
        assertEquals(result.size(), expected.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i).getTitle(), expected.get(i).getTitle());
            assertEquals(result.get(i).getContent(), expected.get(i).getContent());
        }
    }


    @DisplayName("findById 메서드 테스트: DB에서 주어진 id를 가진 게시글 조회")
    @Test
    public void findById_Test(){
        //given
        Article savedArticle = blogRepository.save(Article.builder()
                .title("제목1")
                .content("내용1")
                .build());

        //when
        Article result = blogService.findById(savedArticle.getId());

        //then
        assertEquals(savedArticle.getTitle(), result.getTitle());
        assertEquals(savedArticle.getContent(), result.getContent());
    }


    @DisplayName("delete 메서드 테스트: DB에서 주어진 id를 가진 게시글 삭제")
    @Test
    @Transactional
    public void delete_Test(){
        //given
        Article savedArticle = blogRepository.save(Article.builder()
                .title("제목1")
                .content("내용1")
                .build());

        Long articleId = savedArticle.getId();

        //when
        blogService.delete(articleId);

        //then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }


    @DisplayName("update 메서드 테스트: ")
    @Test
    @Transactional
    public void update_Test(){
        //then
        Article savedArticle = blogRepository.save(Article.builder()
                .title("제목")
                .content("내용")
                .build());

        UpdateArticleRequest request = UpdateArticleRequest.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        //when
        Article result = blogService.update(savedArticle.getId(),request);

        //then
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getContent(), result.getContent());
    }
}