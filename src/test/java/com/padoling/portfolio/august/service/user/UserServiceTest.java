package com.padoling.portfolio.august.service.user;

import com.padoling.portfolio.august.domain.user.Provider;
import com.padoling.portfolio.august.domain.user.Role;
import com.padoling.portfolio.august.domain.user.User;
import com.padoling.portfolio.august.domain.user.UserRepository;
import com.padoling.portfolio.august.web.dto.user.UserNickUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void testIsNicknameExist() {
        //given
        String nickname = "test nickname";

        userRepository.save(User.builder()
                .name("name")
                .email("email")
                .nickname(nickname)
                .role(Role.USER)
                .build());

        //when
        boolean check1 = userService.isNicknameExist(nickname);
        boolean check2 = userService.isNicknameExist("nickname");

        //then
        assertThat(check1).isNotNull();
        assertThat(check1).isEqualTo(true);
        assertThat(check2).isNotNull();
        assertThat(check2).isEqualTo(false);
    }

    @Test
    public void testUpdateNickname() {
        //given
        String name = "name";
        String email = "email";
        String nickname = "nickname";

        userRepository.save(User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build());

        Long userId = userRepository.findAll().get(0).getId();

        UserNickUpdateRequestDto requestDto = UserNickUpdateRequestDto.builder()
                .id(userId)
                .nickname(nickname)
                .build();

        //when
        User user = userService.updateNickname(requestDto);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
    }
}
