package com.padoling.portfolio.august.domain.posts;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.user.User;
import com.padoling.portfolio.august.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setRepositories() {
        userRepository.save(User.builder()
                .name("test name")
                .email("test email")
                .picture("test picture")
                .nickname("test nickname")
                .build());

        bookRepository.save(Book.builder()
                .title("test title")
                .link("test link")
                .build());
    }

    @After
    public void cleanup() {
        postsRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testLoadingPosts() {
        //given
        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        List<Book> bookList = bookRepository.findAll();
        Book book = bookList.get(0);

        String subject = "test subject";
        String content = "test content";

        postsRepository.save(Posts.builder()
                .subject(subject)
                .content(content)
                .user(user)
                .book(book)
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getSubject()).isEqualTo(subject);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getUser().getId()).isEqualTo(user.getId());
        assertThat(posts.getBook().getId()).isEqualTo(book.getId());
        assertThat(posts.getViewCount()).isEqualTo(0);
    }

    @Test
    public void testBaseTimeEntity() {
        //given
        LocalDateTime now = LocalDateTime.of(2020,1,28,0,0,0);
        postsRepository.save(Posts.builder()
                .subject("subject")
                .content("content")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
