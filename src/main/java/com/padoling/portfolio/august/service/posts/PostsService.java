package com.padoling.portfolio.august.service.posts;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.web.dto.PostsResponseDto;
import com.padoling.portfolio.august.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final BookRepository bookRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + requestDto.getBookId()));
        return postsRepository.save(Posts.builder()
                .subject(requestDto.getSubject())
                .content(requestDto.getContent())
                .book(book)
                .build()).getId();
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
