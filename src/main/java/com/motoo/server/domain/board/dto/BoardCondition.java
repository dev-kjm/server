package com.motoo.server.domain.board.dto;

import com.motoo.server.domain.board.domain.BoardType;
import com.motoo.server.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BoardCondition {

    private BoardType boardType;
    private Member member;

}
