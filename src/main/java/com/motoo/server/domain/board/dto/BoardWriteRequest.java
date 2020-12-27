package com.motoo.server.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardWriteRequest {

    // 제목
    @NotEmpty
    private String title;

    // 내용
    @NotEmpty
    private String contents;

    private MultipartFile attachedFile;

}
