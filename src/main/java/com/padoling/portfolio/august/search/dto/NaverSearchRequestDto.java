package com.padoling.portfolio.august.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverSearchRequestDto {

    private String query;

    @Builder.Default
    private Integer display = 10;

    private Integer start;

}
