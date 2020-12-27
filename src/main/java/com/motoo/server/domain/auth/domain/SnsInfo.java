package com.motoo.server.domain.auth.domain;

import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "snsNo", callSuper = false)
@Entity
@Table(name = "sns_info")
public class SnsInfo extends BaseTimeEntity {
    // sns 번호
    @Id
    @GeneratedValue
    @Column(name = "sns_no")
    private Long snsNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    // sns 아이디 sns 유니크 아이디
    @Column(name = "sns_id")
    private String snsId;

    // sns 업체
    @Column(name = "sns_provider")
    private String snsProvider;

    // 액세스 토큰
    @Column(name = "access_token")
    private String accessToken;

    // 리프레쉬 토큰
    @Column(name = "refresh_token")
    private String refreshToken;

    //사용여부
    @Column(name = "user_yn")
    private String userYn;

    @Builder
    public SnsInfo(Member member, String snsId, String snsProvider,
                   String accessToken, String refreshToken, String userYn) {
        this.member = member;
        this.snsId = snsId;
        this.snsProvider = snsProvider;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userYn = userYn;
    }

}