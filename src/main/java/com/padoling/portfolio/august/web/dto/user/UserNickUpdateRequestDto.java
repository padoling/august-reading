package com.padoling.portfolio.august.web.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNickUpdateRequestDto {

    private Long id;
    private String nickname;

}
