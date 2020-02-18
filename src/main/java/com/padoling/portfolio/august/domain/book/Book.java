package com.padoling.portfolio.august.domain.book;

import com.padoling.portfolio.august.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String link;

    private String image;

    private String author;

    private String publisher;

    private String isbn;

    private String description;

    private String pubdate;

    @Builder
    public Book(String title, String link, String image, String author, String publisher, String isbn, String description, String pubdate) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.description = description;
        this.pubdate = pubdate;
    }

    public Book update(String title, String link, String image, String author, String publisher, String isbn, String description, String pubdate) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.description = description;
        this.pubdate = pubdate;

        return this;
    }
}
