package com.example.nishantpuria.bank.api.request;

import com.example.nishantpuria.bank.api.shared.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateBankAccountRequest {

    private final @NotBlank String name;
    private final @NotNull AccountType accountType;

    public CreateBankAccountRequest(
            String name,
            AccountType accountType
    ) {
        this.name = name;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}