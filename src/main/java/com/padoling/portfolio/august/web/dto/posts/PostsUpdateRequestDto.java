package com.padoling.portfolio.august.web.dto.posts;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsUpdateRequestDto {
    private String subject;
    private String content;
}
