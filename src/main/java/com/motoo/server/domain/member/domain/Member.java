package com.motoo.server.domain.member.domain;

import com.motoo.server.domain.member.dto.BankCode;
import com.motoo.server.domain.member.dto.Gender;
import com.motoo.server.domain.model.MemberRoles;
import com.motoo.server.domain.model.MemberState;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "memberNo")
@Entity
@Table(name = "member")
public class Member implements UserDetails {

    // 회원 번호
    @Id
    @GeneratedValue
    @Column(name = "member_no")
    private Long memberNo;

    // 이메일
    @Column(name = "email")
    private String email;

    // 비밀번호
    @Column(name = "pwd")
    private String pwd;

    // 이름
    @Column(name = "name")
    private String name;

    // 닉네임
    @Column(name = "nickname")
    private String nickname;

    // 전화 번호
    @Column(name = "phone_no")
    private String phoneNo;

    // 성별
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 생년월일
    @Column(name = "birthday")
    private String birthday;

    // 국가 번호
    @Column(name = "country_no")
    private String countryNo;

    // 추천인
    @Column(name = "recommender")
    private String recommender;

    // 회원 상태 Y:정상 N:탈퇴
    @Column(name = "member_state")
    @Enumerated(EnumType.STRING)
    private MemberState memberState;

    // 프로필 이미지경로
    @Column(name = "profile_image_path")
    private String profileImagePath;

    // 회원 권한
    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRoles memberRole;

    // 리프레쉬 토큰
    @Column(name = "refresh_token")
    private String refreshToken;

    // 가입 일시
    @Column(name = "join_datetime", updatable = false)
    private LocalDateTime joinDatetime;

    // 수정 일시
    @Column(name = "modify_datetime")
    private LocalDateTime modifyDatetime;

    @Column(name = "bank_code")
    @Enumerated(EnumType.STRING)
    private BankCode bankCode;

    @Column(name = "account_no")
    private String accountNumber;

    // 탈퇴 일시
    @Column(name = "drop_datetime")
    private LocalDateTime dropDatetime;


    @Builder
    public Member(Long memberNo, String email, String pwd, String name, String nickname, String phoneNo, Gender gender,
                  String birthday, String countryNo, String recommender, MemberState memberState, String refreshToken,
                  MemberRoles memberRole, LocalDateTime joinDatetime, LocalDateTime modifyDatetime,
                  LocalDateTime dropDatetime, String profileImagePath, BankCode bankCode, String accountNumber) {
        this.memberNo = memberNo;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nickname = nickname;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.birthday = birthday;
        this.countryNo = countryNo;
        this.recommender = recommender;
        this.memberState = memberState;
        this.refreshToken = refreshToken;
        this.memberRole = memberRole;
        this.joinDatetime = joinDatetime;
        this.dropDatetime = dropDatetime;
        this.modifyDatetime = modifyDatetime;
        this.profileImagePath = profileImagePath;
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }


    public void changeMemberState(MemberState memberState) {
        this.memberState = memberState;
    }


    public void addMemberRole(MemberRoles memberRole) {
        this.memberRole = memberRole;
    }

    public void changeProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePhoneNumber(String countryNo, String phoneNo) {
        this.countryNo = countryNo;
        this.phoneNo = phoneNo;
    }

    public void changeBankAccount(BankCode bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public void changePassword(String pwd) {
        this.pwd = pwd;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.joinDatetime = now;
        this.modifyDatetime = now;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(memberRole.name()));
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.memberNo);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}



