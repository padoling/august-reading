package com.padoling.portfolio.august.domain.book;

import com.padoling.portfolio.august.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicInsert
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

    private String pubdate;

    @ColumnDefault("1")
    private Integer postsCount;

    @Builder
    public Book(String title, String link, String image, String author, String publisher, String pubdate, Integer postsCount) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.postsCount = postsCount;
    }

    public void update(Integer postsCount) {
        this.postsCount = postsCount;
    }
}
