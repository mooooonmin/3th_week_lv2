package com.level2.books.repository;

import com.level2.books.entity.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRecordRepository extends JpaRepository<LoanRecord, Long> {
    Optional<LoanRecord> findByBookIdAndIsReturnedFalse(Long bookId);
    boolean existsByMemberIdAndIsReturnedFalse(Long memberId);
    Optional<LoanRecord> findByBookIdAndMemberIdAndIsReturnedFalse(Long bookId, Long memberId);
    List<LoanRecord> findByMemberIdOrderByLoanDateAsc(Long memberId);
    List<LoanRecord> findByMemberIdAndIsReturnedFalseOrderByLoanDateAsc(Long memberId);
}