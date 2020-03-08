package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {

    private Long id;
    private Long bookId;
    private String author;
    private String subject;
    private String content;
    private Long viewCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.bookId = entity.getBook().getId();
        this.author = entity.getUser().getNickname();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.viewCount = entity.getViewCount();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
