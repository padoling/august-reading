package com.padoling.portfolio.august.service.posts;

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
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
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
    public void cleanup() {
        postsRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testPostsSave() {
        //given
        Long bookId = bookRepository.findAll().get(0).getId();
        Long userId = userRepository.findAll().get(0).getId();

        String subject = "test subject";
        String content = "test content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .subject(subject)
                .content(content)
                .userId(userId)
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
        assertThat(posts.getUser().getId()).isEqualTo(userId);
        assertThat(posts.getBook().getId()).isEqualTo(bookId);
        assertThat(posts.getViewCount()).isEqualTo(0);
    }

    @Test
    public void testPostsUpdate() {
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

        //when
        Long updatedId = postsService.update(postId, requestDto);

        //then
        Posts updatedPost = postsRepository.findById(postId)
                .orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getId()).isEqualTo(updatedId);
        assertThat(updatedPost.getSubject()).isEqualTo(updateSubject);
        assertThat(updatedPost.getContent()).isEqualTo(updateContent);
    }

    @Test
    public void testPostsDelete() {
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

        //when
        postsService.delete(id);

        //then
        Posts posts = postsRepository.findById(id)
                .orElse(null);
        assertThat(posts).isNull();
    }
}
