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
        List<Book> bookList = bookRepository.findAllAsc();

        //then
        Book book = bookList.get(0);
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getIsbn()).isEqualTo(isbn);
    }
}
