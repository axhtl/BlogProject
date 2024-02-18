package com.example.blog.entity;

import com.example.blog.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntityTest {

    private Article article;

    @BeforeEach // 각각의 테스트가 실행될 때마다 이전에 생성된 객체가 사라짐
    public void setUp(){
        this.article = new Article();
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
    }
