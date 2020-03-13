package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsRecentResponseDto {

    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createdDate;
    private String author;
    private Long bookId;
    private String bookTitle;
    private String bookImage;

    public PostsRecentResponseDto(Posts entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        if(entity.getContent().length() >= 28) {
            this.content = this.content.substring(0, 29);
        }
        this.createdDate = entity.getCreatedDate();
        this.author = entity.getUser().getNickname();
        this.bookId = entity.getBook().getId();
        this.bookTitle = entity.getBook().getTitle();
        this.bookImage = entity.getBook().getImage();
    }
}