package com.motoo.server.domain.board.domain;

import com.motoo.server.global.common.BaseTimeEntity;
import com.motoo.server.global.common.UseYn;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "fileNo", callSuper = false)
@ToString(exclude = "board")
@Entity
@Table(name = "file_info")
public class FileInfo extends BaseTimeEntity {

    // 파일 번호
    @Id
    @GeneratedValue
    @Column(name = "contents")
    private Long fileNo;

    // 게시글 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;


    // 저장 파일 이름
    @Column(name = "save_fileName")
    private String saveFileName;

    // 원본 파일 이름
    @Column(name = "original_fileName")
    private String originalFileName;

    // 사용 여부
    @Column(name = "use_yn")
    private UseYn useYn;

    @Builder
    public FileInfo(Board board, String saveFileName, String originalFileName, UseYn useYn) {
        this.board = board;
        this.saveFileName = saveFileName;
        this.originalFileName = originalFileName;
        this.useYn = useYn;
    }
}
