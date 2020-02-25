package com.padoling.portfolio.august.domain.posts;

import com.padoling.portfolio.august.domain.book.Book;

import java.util.List;

public interface PostsRepositoryCustom {
    Long countByBookId(Book book);
    List<Posts> findAllDesc();
}
