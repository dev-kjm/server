package com.motoo.server.domain.board.domain;

import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.global.common.BaseTimeEntity;
import com.motoo.server.global.common.UseYn;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "boardNo", callSuper = false)
@Entity
@Table(name = "board")
@ToString
public class Board extends BaseTimeEntity {

    // 게시글 번호
    @Id
    @GeneratedValue
    @Column(name = "board_no")
    private Long boardNo;

    // 회원 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    // 게시글 타입
    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    // 제목
    @Column(name = "title")
    private String title;

    // 내용
    @Column(name = "contents")
    private String contents;

    // 조회수
    @Column(name = "views")
    private Integer views;

    // 사용 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private UseYn useYn;

    @Builder
    public Board(Member member, BoardType boardType, String title, String contents, Integer views, UseYn useYn) {
        this.member = member;
        this.boardType = boardType;
        this.title = title;
        this.contents = contents;
        this.views = views;
        this.useYn = useYn;
    }

    @PrePersist
    public void prePersist() {
        this.views = 0;
    }

}
