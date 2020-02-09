package com.padoling.portfolio.august.search.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverSearchRequestDto {

    private String query;
    private Integer display;
    private Integer start;
}
