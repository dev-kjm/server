package com.motoo.server.domain.market.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class StockDto {

    private String StockCode;

    private String companyName;

    private String curJuka;

    //매수가

    //매도가

    //전일자 대비


}
