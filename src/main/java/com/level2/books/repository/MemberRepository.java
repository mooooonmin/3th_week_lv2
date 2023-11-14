package com.level2.books.repository;

import com.level2.books.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByPersonalId(String personalId);
    boolean existsByPhoneNumber(String phoneNumber);
}