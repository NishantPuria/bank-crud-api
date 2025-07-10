package com.example.nishantpuria.bank.api.response;

import com.example.nishantpuria.bank.api.shared.AccountType;
import com.example.nishantpuria.bank.api.shared.Currency;
import jakarta.validation.constraints.*;

import java.time.Instant;

public class BankAccountResponse extends ResponseObject {

    private final @NotBlank @Pattern(regexp = "^01\\d{6}$") String accountNumber;
    private final @NotBlank @Pattern(regexp = "^10-10-10$") String sortCode;
    private final @NotBlank String name;
    private final @NotNull AccountType accountType;
    private final @NotNull @DecimalMin("0.00") @DecimalMax("10000.00") Double balance;
    private final @NotNull Currency currency;
    private final @NotNull Instant createdTimestamp;
    private final @NotNull Instant updatedTimestamp;

    public BankAccountResponse(
            String message,
            String accountNumber,
            String sortCode,
            String name,
            AccountType accountType,
            Double balance,
            Currency currency,
            Instant createdTimestamp,
            Instant updatedTimestamp
    ) {
        super(message);
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.name = name;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public String getName() {
        return name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

}