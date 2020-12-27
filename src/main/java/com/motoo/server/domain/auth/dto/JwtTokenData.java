package com.motoo.server.domain.auth.dto;

import com.motoo.server.domain.model.MemberRoles;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtTokenData {
    private Long memberNo;
    private String email;
    private MemberRoles role;
}
