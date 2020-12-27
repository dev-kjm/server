package com.motoo.server.setup;

import com.motoo.server.config.TestProfile;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.dto.Gender;
import com.motoo.server.domain.member.repository.MemberRepository;
import com.motoo.server.domain.model.MemberRoles;
import com.motoo.server.domain.model.MemberState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile(TestProfile.TEST)
@RequiredArgsConstructor
@Component
public class MemberSetup {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private String email = "robotwar12@naver.com";
    private String pwd = "test1234";
    private Gender gender = Gender.FEMALE;
    private String birthday = "19840501";
    private String nickname = "로보트";
    private String phoneNo = "01097950034";
    private String photoPath = "/photo";
    private String recommender = "testMan";
    private String countryNo = "82";
    private MemberState memberState = MemberState.NORMAL;
    private String name = "김정민";
    private MemberRoles role = MemberRoles.ROLE_USER;
    public Member save() {

        return memberRepository.save(Member.builder()
                .email(email)
                .birthday(birthday)
                .countryNo(countryNo)
                .gender(gender)
                .memberState(memberState)
                .name(name)
                .nickname(nickname)
                .phoneNo(phoneNo)
                .pwd(passwordEncoder.encode(pwd))
                .memberRole(role)
                .build());
    }

    public Member withEmailAndPassword(String paramEmail, String paramPwd) {

        return memberRepository.save(Member.builder()
                .email(paramEmail)
                .birthday(birthday)
                .countryNo(countryNo)
                .gender(gender)
                .memberState(memberState)
                .name(name)
                .nickname(nickname)
                .phoneNo(phoneNo)
                .pwd(passwordEncoder.encode(paramPwd))
                 .memberRole(role)
                .build());

    }
}
