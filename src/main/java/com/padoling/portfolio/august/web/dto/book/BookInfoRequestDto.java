package com.padoling.portfolio.august.web.dto.book;

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
