package com.padoling.portfolio.august.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NaverSearchResponseDto {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<Item> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    private static class Item {
        private String title;
        private String link;
        private String image;
        private String author;
        private Integer price;
        private Integer discount;
        private String publisher;
        private String isbn;
        private String description;
        private String pubdate;
    }
}
