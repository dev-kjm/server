package com.motoo.server.domain.board.application;

import com.motoo.server.domain.board.domain.Board;
import com.motoo.server.domain.board.domain.BoardType;
import com.motoo.server.domain.board.domain.Comment;
import com.motoo.server.domain.board.domain.FileInfo;
import com.motoo.server.domain.board.dto.*;
import com.motoo.server.domain.board.repository.BoardRepository;
import com.motoo.server.domain.board.repository.CommentRepository;
import com.motoo.server.domain.board.repository.FileInfoRepository;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.global.common.UseYn;
import com.motoo.server.global.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FileInfoRepository fileInfoRepository;
    private final FileUploadUtil fileUploadUtil;

    public Long boardWrite(Member member, BoardType boardType, BoardWriteRequest request) {

        Board board = Board.builder()
                .member(member)
                .title(request.getTitle())
                .contents(request.getContents())
                .boardType(boardType)
                .useYn(UseYn.Y)
                .build();

        Board saveBoard = boardRepository.save(board);

        MultipartFile attachedFile = request.getAttachedFile();
        if (attachedFile != null) {
            String uploadFilePath = fileUploadUtil.upload(attachedFile);
            fileInfoRepository.save(FileInfo.builder()
                    .board(board)
                    .useYn(UseYn.Y)
                    .originalFileName(attachedFile.getOriginalFilename())
                    .saveFileName(uploadFilePath)
                    .build());
        }

        return saveBoard.getBoardNo();
    }


    public Page<BoardListResponse> findByMemberBoards(Member member, BoardType boardType, Pageable pageable) {
        BoardCondition boardCondition = BoardCondition.builder()
                .boardType(boardType)
                .member(member)
                .build();
        return boardRepository.search(boardCondition, pageable);
    }


    public BoardDetailResponse getBoardDetail(Member member, Long boardNo, BoardType boardType) {
        Board findBoard = boardRepository.detailBoard(member, boardType, boardNo);
        List<Comment> comments = commentRepository.findByBoardAndUseYn(findBoard, UseYn.Y);
        List<FileInfo> fileInfoList = fileInfoRepository.findByBoardAndUseYn(findBoard, UseYn.Y);


        List<CommentResponse> commentResponseList =
                comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());

        List<FileInfoResponse> fileInfoResponses =
                fileInfoList.stream().map(fileInfo -> new FileInfoResponse(fileInfo)).collect(Collectors.toList());

        return BoardDetailResponse.builder()
                .boardNo(findBoard.getBoardNo())
                .boardType(findBoard.getBoardType())
                .modifyDate(findBoard.getModifyDate())
                .contents(findBoard.getContents())
                .registerDate(findBoard.getRegisterDate())
                .title(findBoard.getTitle())
                .views(findBoard.getViews())
                .comments(commentResponseList)
                .attachFiles(fileInfoResponses)
                .build();

    }

}
