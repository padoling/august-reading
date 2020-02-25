package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.search.dto.NaverSearchRequestDto;
import com.padoling.portfolio.august.search.NaverSearchService;
import com.padoling.portfolio.august.service.book.BookService;
import com.padoling.portfolio.august.service.posts.PostsService;
import com.padoling.portfolio.august.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final NaverSearchService naverSearchService;
    private final BookService bookService;
    private final PostsService postsService;

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

    @GetMapping("/posts")
    public String postsList(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "posts-list";
    }

    @GetMapping("/posts/{id}")
    public String postsDetail(Model model, @PathVariable Long id) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("book", bookService.findById(dto.getBookId()));
        model.addAttribute("post", dto);
        return "posts-detail";
    }

    @GetMapping("/posts/write")
    public String postsWrite(Model model, @RequestParam Long bookId) {
        model.addAttribute("book", bookService.findById(bookId));
        return "posts-write";
    }
}
