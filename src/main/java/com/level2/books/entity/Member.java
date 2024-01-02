package com.level2.books.entity;


import com.level2.books.dto.member.MemberRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "members")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(unique = true)
    private String personalId;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    private LocalDateTime penaltyExpirationDate;

    public Member(MemberRequestDto memberRequestDto) {
        this.name = memberRequestDto.getName();
        this.gender = memberRequestDto.getGender();
        this.personalId = memberRequestDto.getPersonalId();
        this.phoneNumber = memberRequestDto.getPhoneNumber();
        this.address = memberRequestDto.getAddress();
    }
    public void  setPenaltyExpirationDate(LocalDateTime penaltyExpirationDate){
        this.penaltyExpirationDate = penaltyExpirationDate;
    }

}
