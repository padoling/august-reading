package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;

    @GetMapping("/book/list")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.findAllAsc());
        return "book-list";
    }
}
