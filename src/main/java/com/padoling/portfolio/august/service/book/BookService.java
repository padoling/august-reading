package com.padoling.portfolio.august.service.book;

import com.padoling.portfolio.august.domain.book.Book;
import com.padoling.portfolio.august.domain.book.BookRepository;
import com.padoling.portfolio.august.web.dto.book.BookInfoRequestDto;
import com.padoling.portfolio.august.web.dto.book.BookResponseDto;
import com.padoling.portfolio.august.web.dto.book.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

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
    public Long findBookInfo(BookInfoRequestDto requestDto) {
        Book book = bookRepository.findByIsbnAndPubdate(requestDto.getIsbn(), requestDto.getPubdate())
                .orElse(null);
        if(book == null) {
            return null;
        }
        return book.getId();
    }

    public BookResponseDto findById(Long id) {
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id=" + id));

        return new BookResponseDto(entity);
    }
}
