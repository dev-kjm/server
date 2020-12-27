package com.motoo.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberState {
    NORMAL("정상"), DROP("탈퇴");

    private String description;
}
