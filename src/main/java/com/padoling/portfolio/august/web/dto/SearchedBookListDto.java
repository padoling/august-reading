package com.padoling.portfolio.august.web.dto;

import com.padoling.portfolio.august.search.dto.NaverSearchResponseDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchedBookListDto {

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

    public SearchedBookListDto(NaverSearchResponseDto.Item item) {
        this.title = item.getTitle();
        this.link = item.getLink();
        this.image = item.getImage();
        this.author = item.getAuthor();
        this.price = item.getPrice();
        this.discount = item.getDiscount();
        this.publisher = item.getPublisher();
        this.isbn = item.getIsbn();
        this.description = item.getDescription();
        this.pubdate = item.getPubdate();
    }
}
