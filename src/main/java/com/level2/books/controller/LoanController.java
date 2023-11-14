package com.level2.books.controller;

import com.level2.books.dto.loan.LoanRequestDto;
import com.level2.books.dto.loan.LoanResponseDto;
import com.level2.books.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/{bookId}/loans")
    public ResponseEntity<Map<String, Object>> loanBook(@PathVariable Long bookId, @RequestBody LoanRequestDto loanRequestDto) {
        try {
            LoanResponseDto loanResponseDto = loanService.loanBook(bookId, loanRequestDto.getMemberId());
            return new ResponseEntity<>(Map.of("status", "success", "data", loanResponseDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("status", "fail", "message", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{bookId}/return")
    public ResponseEntity<Map<String, Object>> returnBook(@PathVariable Long bookId, @RequestBody LoanRequestDto loanRequestDto) {
        try {
            loanService.returnBook(bookId, loanRequestDto.getMemberId());
            return new ResponseEntity<>(Map.of("status", "success", "message", "도서 반납이 완료되었습니다."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("status", "fail", "message", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}