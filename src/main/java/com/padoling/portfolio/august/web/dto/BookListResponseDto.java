package com.padoling.portfolio.august.web.dto;

import com.padoling.portfolio.august.domain.book.Book;
import lombok.Getter;

@Getter
public class BookListResponseDto {

    private String title;
    private String link;
    private String image;
    private String author;
    private String publisher;
    private String isbn;

    public BookListResponseDto(Book entity) {
        this.title = entity.getTitle();
        this.link = entity.getLink();
        this.image = entity.getImage();
        this.author = entity.getAuthor();
        this.publisher = entity.getPublisher();
        this.isbn = entity.getIsbn();
    }
}
