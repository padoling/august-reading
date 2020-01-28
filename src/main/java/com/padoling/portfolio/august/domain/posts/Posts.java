package com.padoling.portfolio.august.domain.posts;

import com.padoling.portfolio.august.domain.BaseTimeEntity;
import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.user.User;
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
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    @ColumnDefault("0")
    private Integer viewCount;

    @Builder
    public Posts(String title, String content, User user, Book book, Integer viewCount) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.book = book;
        this.viewCount = viewCount;
    }

    public void update(String title, String content, Integer viewCount) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }
}
