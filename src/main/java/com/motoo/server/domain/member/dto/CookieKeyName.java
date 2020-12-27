package com.motoo.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CookieKeyName {
    SNS_INFO_COOKIE("sns에서 가져온 정보");

    private String description;
}
