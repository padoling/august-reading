package com.padoling.portfolio.august.domain.stars;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StarsRepositoryTest {

    @Autowired
    private StarsRepository starsRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

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
        starsRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testLoadingStars() {
        //given
        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        List<Book> bookList = bookRepository.findAll();
        Book book = bookList.get(0);

        Integer score = 5;

        starsRepository.save(Stars.builder()
                .score(score)
                .book(book)
                .user(user)
                .build());

        //when
        List<Stars> starsList = starsRepository.findAll();

        //then
        Stars stars = starsList.get(0);
        assertThat(stars.getScore()).isEqualTo(score);
        assertThat(stars.getUser().getId()).isEqualTo(user.getId());
        assertThat(stars.getBook().getId()).isEqualTo(book.getId());
    }
}
