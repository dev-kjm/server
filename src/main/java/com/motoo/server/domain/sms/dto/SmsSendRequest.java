package com.motoo.server.domain.sms.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmsSendRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String phoneNo;
}
