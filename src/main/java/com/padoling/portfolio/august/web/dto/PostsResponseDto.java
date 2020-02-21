package com.padoling.portfolio.august.web.dto;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {

    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.createdTime = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
