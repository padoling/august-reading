package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListBookResponseDto {

    private Long id;
    private String subject;
    private LocalDateTime createdDate;
    private String author;
    private Long viewCount;

    public PostsListBookResponseDto(Posts entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.createdDate = entity.getCreatedDate();
        this.author = entity.getUser().getNickname();
        this.viewCount = entity.getViewCount();
    }
}
