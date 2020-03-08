package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {

    private Long id;
    private String subject;
    private LocalDateTime createdDate;
    private String author;
    private Long viewCount;
    private String bookTitle;
    private String bookImage;
    private String bookAuthor;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.createdDate = entity.getCreatedDate();
        this.author = entity.getUser().getNickname();
        this.viewCount = entity.getViewCount();
        this.bookTitle = entity.getBook().getTitle();
        this.bookImage = entity.getBook().getImage();
        this.bookAuthor = entity.getBook().getAuthor();
    }
}
