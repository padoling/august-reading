package com.padoling.portfolio.august.service.book;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.web.dto.BookInfoRequestDto;
import com.padoling.portfolio.august.web.dto.BookInfoResponseDto;
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

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testBookSaveOrUpdate() {
        //given
        String title = "test title";
        String author = "test author";
        String isbn = "test isbn";
        String pubdate = "test pubdate";

        String newTitle = "new title";
        String newAuthor = "new Author";

        BookSaveRequestDto requestDto = BookSaveRequestDto.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .pubdate(pubdate)
                .build();

        BookSaveRequestDto newRequestDto = BookSaveRequestDto.builder()
                .title(newTitle)
                .author(newAuthor)
                .isbn(isbn)
                .pubdate(pubdate)
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

    @Test
    public void findBookInfo() {
        //given
        String isbn = "test isbn";
        String pubdate = "test pubdate";

        bookRepository.save(Book.builder()
                .isbn(isbn)
                .pubdate(pubdate)
                .build());

        Book book = bookRepository.findByIsbnAndPubdate(isbn, pubdate)
                .orElse(null);

        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .book(book)
                .build());

        BookInfoRequestDto requestDto1 = BookInfoRequestDto.builder()
                .isbn(isbn)
                .pubdate(pubdate)
                .build();
        BookInfoRequestDto requestDto2 = BookInfoRequestDto.builder()
                .isbn("isbn")
                .pubdate("pubdate")
                .build();

        //when
        BookInfoResponseDto responseDto1 = bookService.findBookInfo(requestDto1);
        BookInfoResponseDto responseDto2 = bookService.findBookInfo(requestDto2);

        //then
        assertThat(responseDto1).isNotNull();
        assertThat(responseDto1.getBookId()).isEqualTo(book.getId());
        assertThat(responseDto1.getPostsCount()).isEqualTo(1);
        assertThat(responseDto1.getStarScore()).isNull();
        assertThat(responseDto2).isNotNull();
        assertThat(responseDto2.getBookId()).isNull();
        assertThat(responseDto2.getPostsCount()).isEqualTo(0);
    }
}
