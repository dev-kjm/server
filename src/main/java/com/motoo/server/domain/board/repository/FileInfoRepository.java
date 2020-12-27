package com.motoo.server.domain.board.repository;

import com.motoo.server.domain.board.domain.Board;
import com.motoo.server.domain.board.domain.FileInfo;
import com.motoo.server.global.common.UseYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    List<FileInfo> findByBoardAndUseYn(Board findBoard, UseYn y);
}
