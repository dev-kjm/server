package com.motoo.server.domain.board.domain;

import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.global.common.BaseTimeEntity;
import com.motoo.server.global.common.UseYn;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "commentNo", callSuper = false)
@ToString(exclude = "board")
@Entity

@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    // 댓글 번호
    @Id
    @GeneratedValue
    @Column(name = "comment_no")
    private Long commentNo;

    // 게시글 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;

    // 회원 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    // 내용
    @Column(name = "contents")
    private String contents;

    // 사용 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private UseYn useYn;

    @Builder
    public Comment(Board board, Member member, String contents, UseYn useYn) {
        this.board = board;
        this.member = member;
        this.contents = contents;
        this.useYn = useYn;
    }
}
