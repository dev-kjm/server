package com.motoo.server.domain.member.domain;

import com.motoo.server.domain.member.dto.BankCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class BankAccount {
    @Column(name = "bank_code")
    @Enumerated(EnumType.STRING)
    private BankCode bankCode;

    @Column(name = "account_no")
    private String accountNumber;

    public BankAccount(BankCode bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
