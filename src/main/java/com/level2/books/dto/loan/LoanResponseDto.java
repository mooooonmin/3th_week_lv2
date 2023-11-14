package com.level2.books.dto.loan;

import com.level2.books.entity.Member;
import com.level2.books.entity.Book;
import com.level2.books.entity.LoanRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponseDto {
    private Long loanId;
    private Long bookId;
    private Long memberId;
    private LocalDateTime loanDate;
    private boolean isReturned;

    public LoanResponseDto(LoanRecord newLoan, Book book, Member member) {
        this.loanId = newLoan.getId();
        this.bookId = book.getId();
        this.memberId = member.getId();
        this.loanDate = newLoan.getLoanDate();
        this.isReturned = newLoan.getIsReturned();
    }
}