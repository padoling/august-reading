package com.padoling.portfolio.august.domain.user;

import com.padoling.portfolio.august.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String picture;

    private String nickname;

    @Builder
    public User(String name, String email, String picture, String nickname) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.nickname = nickname;
    }

    public User update(String name, String picture, String nickname) {
        this.name = name;
        this.picture = picture;
        this.nickname = nickname;

        return this;
    }
}
