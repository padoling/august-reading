package com.padoling.portfolio.august.config.auth.dto;

import com.padoling.portfolio.august.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String picture;
    private String nickname;
    private String provider;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.nickname = user.getNickname();
        this.provider = user.getProviderTitle();
    }
}
