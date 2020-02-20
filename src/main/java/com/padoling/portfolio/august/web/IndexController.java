package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.NaverSearchService;
import com.padoling.portfolio.august.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final NaverSearchService naverSearchService;
    private final BookService bookService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/book/list")
    public String bookList(Model model, NaverSearchRequestDto requestDto) {
        if(requestDto.getQuery() == null) {
            return "book-list";
        }
        model.addAttribute("books", naverSearchService.searchByQuery(requestDto));
        return "book-list";
    }

    @GetMapping("/posts/write")
    public String postsWrite(Model model, @RequestParam Long bookId) {
        model.addAttribute("book", bookService.findById(bookId));
        return "posts-write";
    }
}
