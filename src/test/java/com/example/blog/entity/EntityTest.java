package com.example.blog.entity;

import com.example.blog.domain.Article;
import com.example.blog.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class EntityTest {

    private Article article;

    @Autowired
    private BlogRepository blogRepository;

    @BeforeEach // 각각의 테스트가 실행될 때마다 이전에 생성된 객체가 사라짐
    public void setUp(){
        this.article = Article.builder()
                .title("Test Title")
                .content("Test content")
                .build();
    }

    @Test
    @DisplayName("10일후 수정 불가능 테스트")
    public void test_updateDisabled_after10Days(){
        LocalDateTime createdAt = LocalDateTime.now().minusDays(10); // 엔티티 생성 시간을 10일전으로 설정(update 메서드가 호출될 때는 now, 현재시간이므로 예외가 발생
        this.article.setCreatedAt(createdAt);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            this.article.update("Updated Title", "Updated Content");
        });

        assertEquals("엔티티 생성 후 10일이 지난 후에는 수정할 수 없습니다.", exception.getMessage());
        }

    @Test
    @DisplayName("9일후 알림 테스트")
    public void test_alarm_after9Days(){
        LocalDateTime createdAt = LocalDateTime.now().minusDays(9);
        this.article.setCreatedAt(createdAt);
        this.article.update("Updated Title", "Updated Content");
    }

    @Test
    @DisplayName("SoftDelete 테스트")
    public void test_softDelete(){
//        blogRepository.save(article);
//
//        blogRepository.delete(article);
//
//        //데이터 조회시 없어졌는지 확인
//        Article deletedArticle = blogRepository.findById(article.getId()).orElse(null);
//        assertNull(deletedArticle);
//
//        //DB에는 남아있는지 확인 & deleted 컬럼이 true인지 확인

        //given
        blogRepository.save(article);

        // when
        blogRepository.delete(article);

        //then
        // 삭제된 게시글이 목록에 포함되어 있지 않음을 확인
        assertFalse(blogRepository.existsById(article.getId()), "Deleted article should not exist in database");

//        // 삭제된 게시글 목록을 조회하여 개수가 1개인지 확인
//        List<Article> deletedArticles = blogRepository.findDeletedArticles();
//        assertEquals(1, deletedArticles.size(), "One deleted article should be found");
//        // 첫 번째 게시글의 deletedAt이 null이 아닌지 확인
//        Article deletedArticle = deletedArticles.get(0);
//        assertNotNull(deletedArticle.getDeletedAt(), "Deleted article should have deletedAt timestamp");
    }
}

