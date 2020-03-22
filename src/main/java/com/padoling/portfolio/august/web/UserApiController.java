package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.config.auth.dto.SessionUser;
import com.padoling.portfolio.august.service.user.UserService;
import com.padoling.portfolio.august.web.dto.user.UserNickUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/api/v1/user/nick")
    public boolean checkNickname(@RequestParam String nickname) {
        return userService.isNicknameExist(nickname);
    }

    @PutMapping("/api/v1/user/{id}")
    public long updateNickname(@PathVariable Long id, @RequestBody UserNickUpdateRequestDto requestDto) {
        requestDto.setId(id);
        SessionUser sessionUser = new SessionUser(userService.updateNickname(requestDto));
        httpSession.setAttribute("user", sessionUser);
        return id;
    }
}
