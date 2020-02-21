package com.padoling.portfolio.august.service.posts;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.service.book.BookService;
import com.padoling.portfolio.august.web.dto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private BookRepository bookRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testPostsSave() {
        //given
        bookRepository.save(Book.builder()
                .isbn("isbn")
                .pubdate("pubdate")
                .build()
        );
        Long bookId = bookRepository.findAll().get(0).getId();

        String subject = "test subject";
        String content = "test content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .subject(subject)
                .content(content)
                .bookId(bookId)
                .build();

        //when
        Long id = postsService.save(requestDto);
        Posts posts = postsRepository.findAll().get(0);

        //then
        assertThat(id).isNotNull();
        assertThat(posts).isNotNull();
        assertThat(posts.getSubject()).isEqualTo(subject);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getBook().getId()).isEqualTo(bookId);
        assertThat(posts.getViewCount()).isEqualTo(0);
    }
}
