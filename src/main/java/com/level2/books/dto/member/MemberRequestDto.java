package com.level2.books.dto.member;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String name;
    private String gender;
    private String personalId;
    private String phoneNumber;
    private String address;
}
