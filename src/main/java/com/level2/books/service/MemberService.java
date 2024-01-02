package com.level2.books.service;

import com.level2.books.dto.loan.LoanHistoryDto;
import com.level2.books.dto.member.MemberRequestDto;
import com.level2.books.dto.member.MemberResponseDto;
import com.level2.books.entity.Book;
import com.level2.books.entity.LoanRecord;
import com.level2.books.entity.Member;
import com.level2.books.repository.BookRepository;
import com.level2.books.repository.LoanRecordRepository;
import com.level2.books.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LoanRecordRepository loanRecordRepository;
    private final BookRepository bookRepository;

    public MemberResponseDto registerMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto);
        if(memberRepository.existsByPersonalId(member.getPersonalId()))
            throw new IllegalArgumentException("이미 회원가입이 되어있는 주민등록번호입니다.");
        if(memberRepository.existsByPhoneNumber(member.getPhoneNumber()))
            throw new IllegalArgumentException("이미 회원가입이 되어있는 전화번호입니다.");
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }

    public List<LoanHistoryDto> getLoansByMember(Long memberId, boolean isReturned) {
        List<LoanRecord> loanRecords;
        if (isReturned) {
            loanRecords = loanRecordRepository.findByMemberIdOrderByLoanDateAsc(memberId);
        } else {
            loanRecords = loanRecordRepository.findByMemberIdAndIsReturnedFalseOrderByLoanDateAsc(memberId);
        }

        return loanRecords.stream().map(loanRecord -> {
            Member member = memberRepository.findById(loanRecord.getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다"));
            Book book = bookRepository.findById(loanRecord.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("도서 정보를 찾을 수 없습니다"));

            String returnStatus = loanRecord.getIsReturned() ? "반납 완료" : "대출 중";

            return new LoanHistoryDto(loanRecord, book, member);
        }).toList();
    }

}
