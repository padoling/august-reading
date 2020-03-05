package com.padoling.portfolio.august.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.web.dto.book.BookSaveRequestDto;
import com.padoling.portfolio.august.web.dto.posts.PostsSaveRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PostsRepository postsRepository;

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
        postsRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testSavePosts() throws Exception {
        //given
        BookSaveRequestDto bookSaveRequestDto = BookSaveRequestDto.builder()
                .title("title")
                .author("author")
                .build();

        String bookUrl = "http://localhost:" + port + "/api/v1/book";

        MvcResult bookResult = mvc.perform(post(bookUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(bookSaveRequestDto)))
                .andExpect(status().isOk())
                .andReturn();
        String bookContentAsString = bookResult.getResponse().getContentAsString();
        Long bookId = objectMapper.readValue(bookContentAsString, Long.class);

        String subject = "test subject";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .subject(subject)
                .content(content)
                .bookId(bookId)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        MvcResult result = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String contentAsString = result.getResponse().getContentAsString();
        Posts posts = postsRepository.findById(objectMapper.readValue(contentAsString, Long.class))
                .orElse(null);
        assertThat(posts).isNotNull();
        assertThat(posts.getSubject()).isEqualTo(subject);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getBook().getId()).isEqualTo(bookId);
        assertThat(posts.getViewCount()).isEqualTo(0);
    }
}
