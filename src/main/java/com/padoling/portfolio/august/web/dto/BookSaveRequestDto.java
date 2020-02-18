package com.padoling.portfolio.august.web.dto;

import com.padoling.portfolio.august.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSaveRequestDto {

    private String title;
    private String link;
    private String image;
    private String author;
    private String publisher;
    private String isbn;
    private String description;
    private String pubdate;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .link(link)
                .image(image)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .description(description)
                .pubdate(pubdate)
                .build();
    }
}
