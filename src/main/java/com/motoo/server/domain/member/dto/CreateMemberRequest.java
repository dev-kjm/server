package com.motoo.server.domain.member.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@ToString
public class CreateMemberRequest {

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

    private String snsJoinYn;

    private MultipartFile profileImage;
}
