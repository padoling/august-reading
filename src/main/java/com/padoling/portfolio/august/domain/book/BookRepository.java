package com.padoling.portfolio.august.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    Optional<Book> findByIsbnAndPubdate(String isbn, String pubdate);
    Book findById(String id);
}
