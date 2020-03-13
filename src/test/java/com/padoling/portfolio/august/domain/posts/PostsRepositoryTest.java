package com.padoling.portfolio.august.domain.posts;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.user.Role;
import com.padoling.portfolio.august.domain.user.User;
import com.padoling.portfolio.august.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
                .role(Role.USER)
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
    public void testFindAll() {
        //given
        postsRepository.save(Posts.builder()
                .subject("subject1")
                .content("content1")
                .build());
        postsRepository.save(Posts.builder()
                .subject("subject2")
                .content("content2")
                .build());
        postsRepository.save(Posts.builder()
                .subject("subject3")
                .content("content3")
                .build());

        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        Page<Posts> postsPage = postsRepository.findAll(pageRequest);

        //then
        assertThat(postsPage).isNotNull();
        assertThat(postsPage.getPageable().getOffset()).isEqualTo(0);
        assertThat(postsPage.getPageable().getPageSize()).isEqualTo(2);
        assertThat(postsPage.getTotalElements()).isEqualTo(3);
        assertThat(postsPage.getNumberOfElements()).isEqualTo(2);
        assertThat(postsPage.isFirst()).isEqualTo(true);

        Posts posts1 = postsPage.getContent().get(0);
        assertThat(posts1).isNotNull();
        assertThat(posts1.getSubject()).isEqualTo("subject1");
        assertThat(posts1.getContent()).isEqualTo("content1");
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

    @Test
    public void testCountByBookId() {
        //given
        Book book = bookRepository.findAll().get(0);

        postsRepository.save(Posts.builder()
                .subject("subject1")
                .content("content1")
                .book(book)
                .build());

        postsRepository.save(Posts.builder()
                .subject("subject2")
                .content("content2")
                .book(book)
                .build());

        Book bookNull = bookRepository.save(Book.builder().build());

        //when
        Long count = postsRepository.countByBookId(book);
        Long countNull = postsRepository.countByBookId(bookNull);

        //then
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(2);
        assertThat(countNull).isNotNull();
        assertThat(countNull).isEqualTo(0);
    }
}
