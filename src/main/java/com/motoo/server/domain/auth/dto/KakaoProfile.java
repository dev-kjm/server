package com.motoo.server.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class KakaoProfile {
    private Long id;
    private Properties properties;
    private kakaoAccount kakao_account;

    @Getter
    @Setter
    @ToString
    public static class Properties {
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
    }

    @Getter
    @Setter
    @ToString
    public static class kakaoAccount {
        private String email;
        private String birthday;
        private String birthyear;
        private String gender;
    }
}