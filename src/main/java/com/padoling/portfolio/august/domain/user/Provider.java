package com.padoling.portfolio.august.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {

    GOOGLE("google", "구글"),
    NAVER("naver", "네이버"),
    KAKAO("kakao", "카카오");

    private final String key;
    private final String title;
}
