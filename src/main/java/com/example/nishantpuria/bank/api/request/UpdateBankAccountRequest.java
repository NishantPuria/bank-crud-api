package com.example.nishantpuria.bank.api.request;

import com.example.nishantpuria.bank.api.shared.AccountType;

import java.util.Optional;

// TODO: think about validation
public class UpdateBankAccountRequest {

    private final String name;
    private final AccountType accountType;

    public UpdateBankAccountRequest(
            String name,
            AccountType accountType
    ) {
        this.name = name;
        this.accountType = accountType;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<AccountType> getAccountType() {
        return Optional.ofNullable(accountType);
    }

}