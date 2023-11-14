package com.level2.books.dto.book;

import com.level2.books.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String language;
    private String publisher;
    private LocalDateTime registrationDate;
    private String isAvailableLoan;

    public BookResponseDto(Book book, boolean isAvailableLoan) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.publisher = book.getPublisher();
        this.registrationDate = book.getRegistrationDate();
        this.isAvailableLoan = isAvailableLoan ? "대출 가능" : "대출 불가";
    }
}
