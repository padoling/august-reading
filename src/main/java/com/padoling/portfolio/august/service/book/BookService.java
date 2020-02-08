package com.padoling.portfolio.august.service.book;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.web.dto.BookListResponseDto;
import com.padoling.portfolio.august.web.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Long saveOrUpdate(BookSaveRequestDto requestDto) {
        Book book = bookRepository.findByIsbn(requestDto.getIsbn())
                .map(entity -> entity.update(requestDto.getTitle(),
                        requestDto.getLink(),
                        requestDto.getImage(),
                        requestDto.getAuthor(),
                        requestDto.getPublisher()))
                .orElse(requestDto.toEntity());

        return bookRepository.save(book).getId();
    }

    @Transactional(readOnly = true)
    public List<BookListResponseDto> findAllAsc() {
        return bookRepository.findAllAsc().stream()
                .map(BookListResponseDto::new)
                .collect(Collectors.toList());
    }

}
