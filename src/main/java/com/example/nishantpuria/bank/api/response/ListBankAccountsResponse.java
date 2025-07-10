package com.example.nishantpuria.bank.api.response;

import jakarta.validation.constraints.NotNull;

public class ListBankAccountsResponse extends ResponseObject {

    private final @NotNull BankAccountResponse[] accounts;

    public ListBankAccountsResponse(String message, BankAccountResponse[] accounts) {
        super(message);
        this.accounts = accounts;
    }

    public BankAccountResponse[] getAccounts() {
        return accounts;
    }

}