package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PostsRecentResponseDto {

    private Long id;
    private String subject;
    private String content;
    private String createdDate;
    private String author;
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookImage;

    public PostsRecentResponseDto(Posts entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.content = adjustContent(entity.getContent());
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.author = entity.getUser().getNickname();
        this.bookId = entity.getBook().getId();
        this.bookTitle = entity.getBook().getTitle();
        this.bookAuthor = entity.getBook().getAuthor();
        this.bookImage = entity.getBook().getImage();
    }

    private String adjustContent(String entityContent) {
        String replacedContent = entityContent.replaceAll("<(/)?([a-zA-Z0-9]*)(\\s[a-zA-Z0-9]*=[^>]*)?(\\s)*(/)?>", " ");
        if(replacedContent.length() >= 80) {
            replacedContent = replacedContent.substring(0, 80) + " ...";
        }
        return replacedContent;
    }
}