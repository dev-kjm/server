package com.motoo.server.domain.board.dto;

import com.motoo.server.domain.board.domain.FileInfo;
import lombok.Data;

@Data
public class FileInfoResponse {
    // 파일 번호
    private Long fileNo;

    // 저장 파일 경로
    private String filePath;


    public FileInfoResponse(FileInfo fileInfo) {
        this.fileNo = fileInfo.getFileNo();
        this.filePath = fileInfo.getSaveFileName();
    }
}
