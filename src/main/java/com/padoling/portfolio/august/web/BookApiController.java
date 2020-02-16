package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.service.book.BookService;
import com.padoling.portfolio.august.web.dto.BookInfoRequestDto;
import com.padoling.portfolio.august.web.dto.BookInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    @GetMapping("/api/v1/book/info")
    public BookInfoResponseDto findBookInfo(BookInfoRequestDto requestDto) {
        return bookService.findBookInfo(requestDto);
    }
}
