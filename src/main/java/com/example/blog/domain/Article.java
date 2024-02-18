package com.example.blog.domain;

import com.example.blog.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="content", nullable=false)
    private String content;

    @Builder
    public Article(String title, String content){
        this.title=title;
        this.content=content;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;

        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(createdAt.toLocalDate(), now.toLocalDate());

        if(daysBetween>=10) {
            throw new RuntimeException("엔티티 생성 후 10일이 지난 후에는 수정할 수 없습니다.");
        }
    }
}
