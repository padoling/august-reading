package com.padoling.portfolio.august.web.dto.posts;

import com.padoling.portfolio.august.domain.posts.Posts;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PostsListResponseDto {

    private Long id;
    private String subject;
    private String content;
    private String createdDate;
    private String author;
    private Long viewCount;
    private Long bookId;
    private String bookTitle;
    private String bookImage;
    private String bookAuthor;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.content = adjustContent(entity.getContent());
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.author = entity.getUser().getNickname();
        this.viewCount = entity.getViewCount();
        this.bookId = entity.getBook().getId();
        this.bookTitle = entity.getBook().getTitle();
        this.bookImage = entity.getBook().getImage();
        this.bookAuthor = entity.getBook().getAuthor();
    }

    private String adjustContent(String entityContent) {
        String replacedContent = entityContent.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", " ");
        if(replacedContent.length() >= 80) {
            replacedContent = replacedContent.substring(0, 80) + " ...";
        }
        return replacedContent;
    }
}
