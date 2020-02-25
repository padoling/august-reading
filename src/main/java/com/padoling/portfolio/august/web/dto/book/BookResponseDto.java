package com.padoling.portfolio.august.web.dto.book;

import com.padoling.portfolio.august.domain.book.Book;
import lombok.Getter;

@Getter
public class BookResponseDto {

    private Long id;
    private String title;
    private String link;
    private String image;
    private String author;
    private String publisher;
    private String isbn;
    private String description;
    private String pubdate;

    public BookResponseDto(Book entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.link = entity.getLink();
        this.image = entity.getImage();
        this.author = entity.getAuthor();
        this.publisher = entity.getPublisher();
        this.isbn = entity.getIsbn();
        this.description = entity.getDescription();
        this.pubdate = entity.getPubdate();
    }
}
