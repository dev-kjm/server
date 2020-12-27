package com.motoo.server.domain.member.dto;

import com.motoo.server.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindMemberResponse {
    // 회원 번호
    private Long memberNo;
    // 이메일
    private String email;
    // 이름
    private String name;
    // 닉네임
    private String nickname;
    // 전화 번호
    private String phoneNo;
    // 생년월일
    private String birthday;
    // 국가 번호
    private String countryNo;
    // 프로필 이미지경로
    private String profileImagePath;
    //은행코드
    private BankCode bankCode;
    //은행계좌번호
    private String accountNumber;


    public FindMemberResponse(Member member) {
        this.memberNo = member.getMemberNo();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.phoneNo = member.getPhoneNo();
        this.birthday = member.getBirthday();
        this.countryNo = member.getCountryNo();
        this.profileImagePath = member.getProfileImagePath();
        this.bankCode = member.getBankCode();
        this.accountNumber = member.getAccountNumber();
    }
}
