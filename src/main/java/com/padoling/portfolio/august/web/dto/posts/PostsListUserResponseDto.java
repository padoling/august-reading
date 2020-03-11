package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListUserResponseDto {

    private Long id;
    private String subject;
    private LocalDateTime createdDate;
    private Long viewCount;
    private Long bookId;
    private String bookTitle;
    private String bookImage;
    private String bookAuthor;

    public PostsListUserResponseDto(Posts entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.createdDate = entity.getCreatedDate();
        this.viewCount = entity.getViewCount();
        this.bookId = entity.getBook().getId();
        this.bookTitle = entity.getBook().getTitle();
        this.bookImage = entity.getBook().getImage();
        this.bookAuthor = entity.getBook().getAuthor();
    }
}
