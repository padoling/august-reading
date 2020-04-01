package com.padoling.portfolio.august.config.auth.dto;

import com.padoling.portfolio.august.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String nickname;

    public SessionUser(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
