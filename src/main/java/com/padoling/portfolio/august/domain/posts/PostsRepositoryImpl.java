package com.padoling.portfolio.august.domain.posts;

import com.padoling.portfolio.august.domain.book.Book;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.padoling.portfolio.august.domain.posts.QPosts.posts;

@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countByBookId(Book book) {
        return queryFactory
                .select(posts.count())
                .from(posts)
                .where(posts.book.eq(book))
                .fetch()
                .get(0);
    }
}
