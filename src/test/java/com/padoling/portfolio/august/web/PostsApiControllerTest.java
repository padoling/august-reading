package com.padoling.portfolio.august.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.domain.user.Role;
import com.padoling.portfolio.august.domain.user.User;
import com.padoling.portfolio.august.domain.user.UserRepository;
import com.padoling.portfolio.august.web.dto.posts.PostsSaveRequestDto;
import com.padoling.portfolio.august.web.dto.posts.PostsUpdateRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private UserRepository userRepository;

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

        bookRepository.save(Book.builder()
                .isbn("isbn")
                .pubdate("pubdate")
                .build());

        userRepository.save(User.builder()
                .name("name")
                .email("email")
                .role(Role.USER)
                .build());
    }

    @After
    public void tearDown() {
        postsRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSavePosts() throws Exception {
        //given
        Long bookId = bookRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        String subject = "test subject";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .subject(subject)
                .content(content)
                .bookId(bookId)
                .userId(userId)
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
        assertThat(posts.getUser().getId()).isEqualTo(userId);
        assertThat(posts.getViewCount()).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdatePosts() throws Exception {
        //given
        Book book = bookRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        postsRepository.save(Posts.builder()
                .subject("subject")
                .content("content")
                .book(book)
                .user(user)
                .build());
        Long postId = postsRepository.findAll().get(0).getId();

        String updateSubject = "update subject";
        String updateContent = "update content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .subject(updateSubject)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + postId;

        //when
        MvcResult result = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String contentAsString = result.getResponse().getContentAsString();
        Posts posts = postsRepository.findById(objectMapper.readValue(contentAsString, Long.class))
                .orElse(null);
        assertThat(posts).isNotNull();
        assertThat(posts.getId()).isEqualTo(postId);
        assertThat(posts.getSubject()).isEqualTo(updateSubject);
        assertThat(posts.getContent()).isEqualTo(updateContent);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeletePosts() throws Exception {
        //given
        Book book = bookRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        postsRepository.save(Posts.builder()
                .subject("subject")
                .content("content")
                .book(book)
                .user(user)
                .build());
        Long id = postsRepository.findAll().get(0).getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        //when
        mvc.perform(delete(url))
                .andExpect(status().isOk());

        //then
        Posts posts = postsRepository.findById(id)
                .orElse(null);
        assertThat(posts).isNull();
    }
}
