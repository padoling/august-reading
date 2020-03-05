package com.padoling.portfolio.august.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.web.dto.book.BookResponseDto;
import com.padoling.portfolio.august.web.dto.book.BookSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BookApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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
    @WithMockUser(roles = "USER")
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
}
