package com.padoling.portfolio.august.domain.stars;

import com.padoling.portfolio.august.domain.BaseTimeEntity;
import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Stars extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    @Builder
    public Stars(Integer score, User user, Book book) {
        this.score = score;
        this.user = user;
        this.book = book;
    }

    public void update(Integer score) {
        this.score = score;
    }
}
