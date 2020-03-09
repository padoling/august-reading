package com.padoling.portfolio.august.domain.posts;

import com.padoling.portfolio.august.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsRepositoryCustom{
    Optional<Posts> findById(Long id);
    List<Posts> findByBook(Book book);
}
