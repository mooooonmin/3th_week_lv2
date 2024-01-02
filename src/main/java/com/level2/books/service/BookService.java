package com.level2.books.service;

import com.level2.books.dto.book.BookRequestDto;
import com.level2.books.dto.book.BookResponseDto;
import com.level2.books.entity.Book;
import com.level2.books.repository.BookRepository;
import com.level2.books.repository.LoanRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final LoanRecordRepository loanRecordRepository;

    public BookResponseDto bookRegistration(BookRequestDto bookRequestDto) {
        Book book = new Book(bookRequestDto);
        book = bookRepository.save(book);
        boolean isAvailable = isBookAvailableForLoan(book.getId());
        return new BookResponseDto(book, isAvailable);
    }

    public List<BookResponseDto> getBookList() {
        return bookRepository.findAllByOrderByRegistrationDateDesc()
                .stream()
                .map(book -> new BookResponseDto(book, isBookAvailableForLoan(book.getId())))
                .toList();
    }

    public BookResponseDto getBook(Long bookId) {
        Book book = findBook(bookId);
        boolean isAvailable = isBookAvailableForLoan(bookId);
        return new BookResponseDto(book, isAvailable);
    }

    private Book findBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 책입니다."));
    }

    private boolean isBookAvailableForLoan(Long bookId) {
        return loanRecordRepository.findByBookIdAndIsReturnedFalse(bookId).isEmpty();
    }

}
