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
    @Column(name = "POSTS_ID")
    private Long id;

    @Column(length = 500, nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ColumnDefault("0")
    private Long viewCount;

    @Builder
    public Posts(String subject, String content, User user, Book book, Long viewCount) {
        this.subject = subject;
        this.content = content;
        this.user = user;
        this.book = book;
        this.viewCount = viewCount;
    }

    public void update(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
}
