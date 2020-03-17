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
        this.content = adjustContent(entity.getContent());
        this.createdDate = entity.getCreatedDate();
        this.author = entity.getUser().getNickname();
        this.bookId = entity.getBook().getId();
        this.bookTitle = entity.getBook().getTitle();
        this.bookImage = entity.getBook().getImage();
    }

    private String adjustContent(String entityContent) {
        String replacedContent = entityContent.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", " ");
        if(replacedContent.length() >= 80) {
            replacedContent = replacedContent.substring(0, 80) + " ...";
        }
        return replacedContent;
    }
}