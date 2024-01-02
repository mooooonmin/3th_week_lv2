package com.level2.books.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRecord{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private Long memberId;

    @Column
    private Boolean isReturned;

    @Column
    private LocalDateTime loanDate;

    @Column
    private LocalDateTime returnDate;

    public LoanRecord(Book book, Member member) {
        this.bookId = book.getId();
        this.memberId = member.getId();
        this.isReturned = false;
        this.loanDate = LocalDateTime.now();
    }
}
