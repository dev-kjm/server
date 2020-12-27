package com.motoo.server.domain.market.domain;

import com.motoo.server.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//@Table(name = "kosdaq")
public class Kosdaq extends BaseTimeEntity {

    // 종목 코드
    private String itemCode;

    // 회사 이름
    private String companyName;


    // 사용 여부
    private String useYn;
}
