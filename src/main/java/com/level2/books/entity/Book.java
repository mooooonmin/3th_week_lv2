package com.level2.books.entity;


import com.level2.books.dto.book.BookRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String language;
    @Column(nullable = false)
    private String publisher;

    public Book(BookRequestDto bookRequestDto) {
        this.title = bookRequestDto.getTitle();
        this.author = bookRequestDto.getAuthor();
        this.language = bookRequestDto.getLanguage();
        this.publisher = bookRequestDto.getPublisher();
    }
}
