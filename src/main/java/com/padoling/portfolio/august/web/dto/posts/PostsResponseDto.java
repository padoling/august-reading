package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {

    private Long id;
    private Long bookId;
    private String subject;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.bookId = entity.getBook().getId();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}