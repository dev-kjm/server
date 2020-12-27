package com.motoo.server.domain.member.dto;

public enum BankCode {
    BANK_004("KB국민은행"),
    BANK_003("기업은행"),
    BANK_020("우리은행"),
    BANK_011("NH농협은행"),
    BANK_088("신한은행"),
    BANK_081("하나은행"),
    BANK_027("한국씨티은행"),
    BANK_023("SC제일은행"),
    BANK_039("경남은행"),
    BANK_034("광주은행"),
    BANK_031("대구은행"),
    BANK_055("도이치은행"),
    BANK_032("부산은행"),
    BANK_061("비엔피파리바은행"),
    BANK_064("산림조합중앙회"),
    BANK_002("산업은행"),
    BANK_045("새마을금고"),
    BANK_008("수출입은행"),
    BANK_007("수협은행"),
    BANK_048("신협"),
    BANK_071("우체국"),
    BANK_050("저축은행"),
    BANK_037("전북은행"),
    BANK_067("중국건설은행"),
    BANK_062("중국공상은행"),
    BANK_090("카카오뱅크"),
    BANK_089("케이뱅크"),
    BANK_060("BOA은행"),
    BANK_054("HSBC은행"),
    BANK_057("제이피모간체이스은행");

    private String name;

    BankCode(String name) {
        this.name = name;
    }

}




