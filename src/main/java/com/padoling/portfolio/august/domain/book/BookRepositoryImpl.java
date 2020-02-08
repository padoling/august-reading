package com.padoling.portfolio.august.domain.book;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.padoling.portfolio.august.domain.book.QBook.book;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Book> findAllAsc() {
        return queryFactory
                .selectFrom(book)
                .orderBy(book.title.asc())
                .fetch();
    }
}
