package com.padoling.portfolio.august.search.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NaverSearchResponseDto {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private String query;
    private boolean isFirst;
    private boolean isLast;
    private List<Item> items;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Item {
        private String title;
        private String link;
        private String image;
        private String author;
        private String price;
        private String discount;
        private String publisher;
        private String isbn;
        private String description;
        private String pubdate;
    }
}
