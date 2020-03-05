package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.config.auth.LoginUser;
import com.padoling.portfolio.august.config.auth.dto.SessionUser;
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
    public String index(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/book/list")
    public String bookList(Model model, @LoginUser SessionUser user, NaverSearchRequestDto requestDto) {
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        if(requestDto.getQuery() == null) {
            return "book-list";
        }
        model.addAttribute("books", naverSearchService.searchByQuery(requestDto));
        return "book-list";
    }

    @GetMapping("/posts")
    public String postsList(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        model.addAttribute("posts", postsService.findAllDesc());
        return "posts-list";
    }

    @GetMapping("/posts/{id}")
    public String postsDetail(Model model, @LoginUser SessionUser user, @PathVariable Long id) {
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("book", bookService.findById(dto.getBookId()));
        model.addAttribute("author", dto.getAuthor());
        model.addAttribute("post", dto);
        return "posts-detail";
    }

    @GetMapping("/posts/write")
    public String postsWrite(Model model, @LoginUser SessionUser user, @RequestParam Long bookId) {
        if(user != null) {
            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getName());
        }
        model.addAttribute("book", bookService.findById(bookId));
        return "posts-write";
    }
}
