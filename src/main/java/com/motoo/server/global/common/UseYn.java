package com.motoo.server.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UseYn {
    Y("사용"), N("미사용");

    private String description;
}
