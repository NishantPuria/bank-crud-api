package com.example.nishantpuria.bank.api.response;

import jakarta.validation.constraints.NotNull;

public class ListTransactionsResponse extends ResponseObject {

    private final @NotNull TransactionResponse[] transactions;

    public ListTransactionsResponse(String message, TransactionResponse[] transactions) {
        super(message);
        this.transactions = transactions;
    }

    public TransactionResponse[] getTransactions() {
        return transactions;
    }

}