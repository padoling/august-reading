package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.web.dto.BookSaveRequestDto;
import com.padoling.portfolio.august.web.dto.PostsSaveRequestDto;
import org.junit.After;
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
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private BookRepository bookRepository;

    @After
    public void tearDown() {
        postsRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testSavePosts() {
        //given
        BookSaveRequestDto bookSaveRequestDto = BookSaveRequestDto.builder()
                .title("title")
                .author("author")
                .build();

        String bookUrl = "http://localhost:" + port + "/api/v1/book";

        Long bookId = restTemplate.postForEntity(bookUrl, bookSaveRequestDto, Long.class).getBody();

        String subject = "test subject";
        String content = "test content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .subject(subject)
                .content(content)
                .bookId(bookId)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Posts posts = postsRepository.findById(responseEntity.getBody())
                .orElse(null);
        assertThat(posts).isNotNull();
        assertThat(posts.getSubject()).isEqualTo(subject);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getBook().getId()).isEqualTo(bookId);
        assertThat(posts.getViewCount()).isEqualTo(0);
    }
}
