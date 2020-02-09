package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.service.search.NaverSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final NaverSearchService naverSearchService;

    @GetMapping("/book/list")
    public String bookList(Model model, NaverSearchRequestDto requestDto) {
        if(requestDto.getQuery() == null) {
            return "book-list";
        }

        model.addAttribute("books", naverSearchService.searchByQuery(requestDto));
        return "book-list";
    }
}
