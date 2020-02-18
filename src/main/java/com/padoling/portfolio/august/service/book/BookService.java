package com.padoling.portfolio.august.service.book;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.domain.posts.PostsRepository;
import com.padoling.portfolio.august.web.dto.BookInfoRequestDto;
import com.padoling.portfolio.august.web.dto.BookInfoResponseDto;
import com.padoling.portfolio.august.web.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    private final PostsRepository postsRepository;

    @Transactional
    public Long saveOrUpdate(BookSaveRequestDto requestDto) {
        Book book = bookRepository.findByIsbnAndPubdate(requestDto.getIsbn(), requestDto.getPubdate())
                .map(entity -> entity.update(requestDto.getTitle(),
                        requestDto.getLink(),
                        requestDto.getImage(),
                        requestDto.getAuthor(),
                        requestDto.getPublisher(),
                        requestDto.getIsbn(),
                        requestDto.getDescription(),
                        requestDto.getPubdate()))
                .orElse(requestDto.toEntity());

        return bookRepository.save(book).getId();
    }

    @Transactional
    public BookInfoResponseDto findBookInfo(BookInfoRequestDto requestDto) {
        Book book = bookRepository.findByIsbnAndPubdate(requestDto.getIsbn(), requestDto.getPubdate())
                .orElse(null);
        if(book == null) {
            return BookInfoResponseDto.builder()
                    .postsCount(0L)
                    .build();
        }
        Long postsCount = postsRepository.countByBookId(book);
        return BookInfoResponseDto.builder()
                .bookId(book.getId())
                .postsCount(postsCount)
                .build();
    }

}
