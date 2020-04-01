package com.padoling.portfolio.august.web.dto.user;

import com.padoling.portfolio.august.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private String nickname;
    private String provider;
    private String role;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.nickname = entity.getNickname();
        this.provider = entity.getProviderTitle();
        this.role = entity.getRoleKey();
    }
}
