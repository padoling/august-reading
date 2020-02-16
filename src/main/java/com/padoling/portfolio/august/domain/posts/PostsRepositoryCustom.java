package com.padoling.portfolio.august.domain.posts;

import com.padoling.portfolio.august.domain.book.Book;

public interface PostsRepositoryCustom {
    Long countByBookId(Book book);
}
