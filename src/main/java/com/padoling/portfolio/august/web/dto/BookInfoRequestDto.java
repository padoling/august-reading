package com.padoling.portfolio.august.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInfoRequestDto {
    private String isbn;
    private String pubdate;
}
