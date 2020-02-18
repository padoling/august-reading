package com.padoling.portfolio.august.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInfoRequestDto {
    private String isbn;
    private String pubdate;
}
