package com.motoo.server.domain.member.dto;

import com.motoo.server.domain.model.SnsProvider;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@AllArgsConstructor
public class SignUpRequest {

    private String email;

    private String pwd;

    private String confirmPwd;

    @NotBlank
    private String phoneNo;

    @NotBlank
    private String countryNo;

    @NotBlank
    private String nickname;

    @NotBlank
    private String name;

    private String gender;

    private String birthday;

    private String recommender;

    private MultipartFile profileImage;

    private String snsId;

    private String snsProfileImagePath;

    private SnsProvider snsProvider;


}
