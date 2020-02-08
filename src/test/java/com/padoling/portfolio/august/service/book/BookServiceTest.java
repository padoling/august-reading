package com.padoling.portfolio.august.service.book;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.web.dto.BookSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @After
    public void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    public void testBookSaveOrUpdate() {
        //given
        String title = "test title";
        String author = "test author";
        String isbn = "test isbn";

        String newTitle = "new title";
        String newAuthor = "new Author";

        BookSaveRequestDto requestDto = BookSaveRequestDto.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .build();

        BookSaveRequestDto newRequestDto = BookSaveRequestDto.builder()
                .title(newTitle)
                .author(newAuthor)
                .isbn(isbn)
                .build();

        //when
        Long id = bookService.saveOrUpdate(requestDto);
        Book book = bookRepository.findAll().get(0);
        Long newId = bookService.saveOrUpdate(newRequestDto);
        Book newBook = bookRepository.findAll().get(0);

        //then
        assertThat(id).isNotNull();
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getIsbn()).isEqualTo(isbn);
        assertThat(newId).isNotNull();
        assertThat(newBook).isNotNull();
        assertThat(newBook.getTitle()).isEqualTo(newTitle);
        assertThat(newBook.getAuthor()).isEqualTo(newAuthor);
        assertThat(newBook.getIsbn()).isEqualTo(isbn);
    }
}
