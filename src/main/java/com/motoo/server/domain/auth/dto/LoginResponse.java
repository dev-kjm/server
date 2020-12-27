package com.motoo.server.domain.auth.dto;

import com.motoo.server.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private Long memberNo;
    private String email;
    private String name;
    private String nickname;

    public LoginResponse(final Member member) {
        this.memberNo = member.getMemberNo();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
    }
}
