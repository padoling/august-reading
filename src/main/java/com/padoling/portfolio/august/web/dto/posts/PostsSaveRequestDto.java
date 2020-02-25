package com.padoling.portfolio.august.web.dto.posts;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsSaveRequestDto {
    private String subject;
    private String content;
    private Long bookId;
}
