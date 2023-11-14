package com.level2.books.dto.book;

import lombok.Getter;

@Getter
public class BookRequestDto {
    private String title;
    private String author;
    private String language;
    private String publisher;
}
