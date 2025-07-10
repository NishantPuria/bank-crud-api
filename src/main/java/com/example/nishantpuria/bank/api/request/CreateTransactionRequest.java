package com.example.nishantpuria.bank.api.request;

import com.example.nishantpuria.bank.api.shared.Currency;
import com.example.nishantpuria.bank.api.shared.TransactionType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class CreateTransactionRequest {

    private final @NotNull @DecimalMin("0.00") @DecimalMax("10000.00") Double amount;
    private final @NotNull Currency currency;
    private final @NotNull TransactionType type;
    private final String reference;

    public CreateTransactionRequest(Double amount, Currency currency, TransactionType type, String reference) {
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.reference = reference;
    }

    public Double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TransactionType getType() {
        return type;
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(reference);
    }

}