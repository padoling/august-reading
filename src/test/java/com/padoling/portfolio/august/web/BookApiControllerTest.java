package com.padoling.portfolio.august.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.web.dto.book.BookInfoRequestDto;
import com.padoling.portfolio.august.web.dto.book.BookResponseDto;
import com.padoling.portfolio.august.web.dto.book.BookSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BookApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveBook() throws Exception {
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
        MvcResult result = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String contentAsString = result.getResponse().getContentAsString();
        Book book = bookRepository.findById(objectMapper.readValue(contentAsString, Long.class))
                .orElse(null);
        assertThat(book).isNotNull();
        BookResponseDto responseDto = new BookResponseDto(book);
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getIsbn()).isEqualTo(isbn);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testFindBook() throws Exception {
        //given
        String title = "test title";
        String isbn = "test isbn";
        String pubdate = "test pubdate";
        bookRepository.save(Book.builder()
                .title(title)
                .isbn(isbn)
                .pubdate(pubdate)
                .build());

        BookInfoRequestDto notSavedRequestDto = BookInfoRequestDto.builder()
                .isbn("isbn")
                .pubdate("pubdate")
                .build();

        BookInfoRequestDto savedRequestDto = BookInfoRequestDto.builder()
                .isbn(isbn)
                .pubdate(pubdate)
                .build();

        String notSavedUrl = "http://localhost:" + port + "/api/v1/book/info?isbn=isbn&pubdate=pubdate";
        String savedUrl = "http://localhost:" + port + "/api/v1/book/info?isbn=" + isbn + "&pubdate=" + pubdate;

        //when
        MvcResult notSavedResult = mvc.perform(get(notSavedUrl))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult savedResult = mvc.perform(get(savedUrl))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String notSavedContent = notSavedResult.getResponse().getContentAsString();
        assertThat(notSavedContent).isEqualTo("");

        String savedContent = savedResult.getResponse().getContentAsString();
        Long savedBookId = bookRepository.findAll().get(0).getId();
        assertThat(savedContent).isNotNull();
        assertThat(savedContent).isEqualTo(savedBookId.toString());
    }
}
