package com.padoling.portfolio.august.web.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BookInfoResponseDto {
    private Long bookId;
    private Long postsCount;
    private Integer starScore;
}
