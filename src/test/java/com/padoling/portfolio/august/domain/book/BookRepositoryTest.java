package com.padoling.portfolio.august.domain.book;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @After
    public void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    public void testLoadingBooks() {
        //given
        String title = "test title";
        String isbn = "123456";

        bookRepository.save(Book.builder()
                .title(title)
                .link("test link")
                .isbn(isbn)
                .build());

        //when
        List<Book> bookList = bookRepository.findAll();

        //then
        Book book = bookList.get(0);
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getIsbn()).isEqualTo(isbn);
    }

    @Test
    public void testFindByIsbnAndPubdate() {
        //given
        String isbn = "test isbn";
        String pubdate = "test pubdate";

        bookRepository.save(Book.builder()
                .isbn(isbn)
                .pubdate(pubdate)
                .build()
        );

        //when
        Book book1 = bookRepository.findByIsbnAndPubdate(isbn, pubdate)
                .orElse(null);
        Book book2 = bookRepository.findByIsbnAndPubdate("isbn", "pubdate")
                .orElse(null);

        //then
        assertThat(book1).isNotNull();
        assertThat(book1.getId()).isEqualTo(1);
        assertThat(book2).isNull();
    }
}
