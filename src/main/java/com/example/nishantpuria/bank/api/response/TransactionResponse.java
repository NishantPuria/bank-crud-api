package com.example.nishantpuria.bank.api.response;

import com.example.nishantpuria.bank.api.shared.Currency;
import com.example.nishantpuria.bank.api.shared.TransactionType;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.Optional;

public class TransactionResponse extends ResponseObject {

    private final @NotBlank @Pattern(regexp = "^tan-[A-Za-z0-9]+$") String id;
    private final @NotNull @DecimalMin("0.00") @DecimalMax("10000.00") Double amount;
    private final @NotNull Currency currency;
    private final @NotNull TransactionType type;
    private final String reference;
    private final @NotBlank @Pattern(regexp = "^usr-[A-Za-z0-9]+$") String userId;
    private final @NotNull Instant createdTimestamp;

    public TransactionResponse(String message, String id, Double amount, Currency currency, TransactionType type, String reference, String userId, Instant createdTimestamp) {
        super(message);
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.reference = reference;
        this.userId = userId;
        this.createdTimestamp = createdTimestamp;
    }

    public String getId() {
        return id;
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

    public String getUserId() {
        return userId;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

}