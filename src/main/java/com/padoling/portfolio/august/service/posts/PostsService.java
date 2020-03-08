package com.padoling.portfolio.august.service.posts;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.domain.user.User;
import com.padoling.portfolio.august.domain.user.UserRepository;
import com.padoling.portfolio.august.web.dto.posts.PostsListResponseDto;
import com.padoling.portfolio.august.web.dto.posts.PostsResponseDto;
import com.padoling.portfolio.august.web.dto.posts.PostsSaveRequestDto;
import com.padoling.portfolio.august.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + requestDto.getUserId()));
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + requestDto.getBookId()));
        return postsRepository.save(Posts.builder()
                .subject(requestDto.getSubject())
                .content(requestDto.getContent())
                .user(user)
                .book(book)
                .build()).getId();
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        entity.update(requestDto.getSubject(), requestDto.getContent());
        return id;
    }

    @Transactional
    public Long updateViewCount(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        entity.updateViewCount(entity.getViewCount() + 1);
        return id;
    }
}
