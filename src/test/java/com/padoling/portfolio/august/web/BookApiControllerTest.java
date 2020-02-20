package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.web.dto.BookResponseDto;
import com.padoling.portfolio.august.web.dto.BookSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BookApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        //given
        String title = "test title";
        String author = "test author";
        String isbn = "test isbn";
        String pubdate = "test pubdate";
        BookSaveRequestDto requestDto = BookSaveRequestDto.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .pubdate(pubdate)
                .build();

        String url = "http://localhost:" + port + "/api/v1/book";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Book book = bookRepository.findById(responseEntity.getBody())
                .orElse(null);
        assertThat(book).isNotNull();
        BookResponseDto responseDto = new BookResponseDto(book);
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getIsbn()).isEqualTo(isbn);
    }
}
