package com.padoling.portfolio.august.service.posts;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.Posts;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.domain.user.User;
import com.padoling.portfolio.august.domain.user.UserRepository;
import com.padoling.portfolio.august.web.dto.posts.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<PostsListBookResponseDto> findByBookId(Long bookId, Pageable pageable) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + bookId));
        return postsRepository.findByBook(book, pageable)
                .map(PostsListBookResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<PostsListUserResponseDto> findByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + userId));
        return postsRepository.findByUser(user, pageable)
                .map(PostsListUserResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> findAll(Pageable pageable) {
        return postsRepository.findAll(pageable)
                .map(PostsListResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<PostsRecentResponseDto> findRecentPosts() {
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "id");

        return postsRepository.findAll(pageRequest)
                .map(PostsRecentResponseDto::new);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        entity.update(requestDto.getSubject(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void updateViewCount(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        entity.updateViewCount(entity.getViewCount() + 1);
    }

    public void delete(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(entity);
    }
}
