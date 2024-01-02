package com.level2.books.service;

import com.level2.books.dto.loan.LoanResponseDto;
import com.level2.books.entity.Book;
import com.level2.books.entity.LoanRecord;
import com.level2.books.entity.Member;
import com.level2.books.repository.BookRepository;
import com.level2.books.repository.LoanRecordRepository;
import com.level2.books.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRecordRepository loanRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LoanResponseDto loanBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 도서ID가 존재하지 않습니다"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원ID는 등록되지 않은 ID입니다"));

        LocalDateTime penaltyExpirationDate = member.getPenaltyExpirationDate();
        if (penaltyExpirationDate != null && penaltyExpirationDate.isAfter(LocalDateTime.now())) {
            long penaltyDays = LocalDateTime.now().until(penaltyExpirationDate, ChronoUnit.DAYS);
            throw new IllegalStateException(String.format("도서 반납이 연체되셨으므로 %d일동안 도서를 대출하실 수 없습니다.", penaltyDays));
        }

        boolean hasNotReturnedLoanBook = loanRecordRepository.existsByMemberIdAndIsReturnedFalse(memberId);
        if (hasNotReturnedLoanBook) {
            throw new IllegalStateException("먼저 대출하신 도서를 반납 후 이용 부탁드립니다.");
        }
        Optional<LoanRecord> theBookIsLoan = loanRecordRepository.findByBookIdAndIsReturnedFalse(bookId);
        if (theBookIsLoan.isPresent()) {
            throw new IllegalStateException("해당 도서는 대출 중입니다.");
        }

        LoanRecord newLoan = new LoanRecord(book, member);
        loanRecordRepository.save(newLoan);

        return new LoanResponseDto(newLoan, book, member);
    }

    @Transactional
    public void returnBook(Long bookId, Long memberId) {
        LoanRecord loanRecord = loanRecordRepository.findByBookIdAndMemberIdAndIsReturnedFalse(bookId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("관리자에게 문의하세요."));

        loanRecord.setIsReturned(true);
        loanRecord.setReturnDate(LocalDateTime.now());

        if (loanRecord.getLoanDate().plusDays(7).isBefore(loanRecord.getReturnDate())){
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException(("해당 회원을 찾을 수 없습니다")));
            member.setPenaltyExpirationDate(LocalDateTime.now().plusWeeks(2));
            memberRepository.save(member);
        }

        loanRecordRepository.save(loanRecord);
    }

}
