package com.example.nishantpuria.bank.utils;

import com.example.nishantpuria.bank.api.request.CreateBankAccountRequest;
import com.example.nishantpuria.bank.api.request.CreateTransactionRequest;
import com.example.nishantpuria.bank.api.request.CreateUserRequest;
import com.example.nishantpuria.bank.api.request.UpdateUserRequest;
import com.example.nishantpuria.bank.api.response.*;
import com.example.nishantpuria.bank.model.table.Account;
import com.example.nishantpuria.bank.model.table.Address;
import com.example.nishantpuria.bank.model.table.Transaction;
import com.example.nishantpuria.bank.model.table.User;
import com.example.nishantpuria.bank.model.type.AccountType;
import com.example.nishantpuria.bank.model.type.Currency;
import com.example.nishantpuria.bank.model.type.TransactionType;

import java.time.Instant;
import java.util.List;

import static com.example.nishantpuria.bank.utils.Formatter.*;

public class Mapper {

    // ----- CREATE DB ENTITIES

    public static User userFrom(CreateUserRequest createUserRequest) {
        com.example.nishantpuria.bank.api.shared.Address address = createUserRequest.getAddress();
        Instant now = Instant.ofEpochMilli(System.currentTimeMillis());

        return new User(
                createUserRequest.getName(),
                new Address(
                        address.getLine1(),
                        address.getLine2().orElse(null),
                        address.getLine3().orElse(null),
                        address.getTown(),
                        address.getCounty(),
                        address.getPostcode()
                ),
                createUserRequest.getPhoneNumber(),
                createUserRequest.getEmail(),
                now,
                now
        );
    }

    public static User userFrom(User existingUser, UpdateUserRequest updateUserRequest) {
        com.example.nishantpuria.bank.api.shared.Address address = updateUserRequest.getAddress();

        return new User(
                existingUser.getId(),
                updateUserRequest.getName(),
                new Address(
                        address.getLine1(),
                        address.getLine2().orElse(null),
                        address.getLine3().orElse(null),
                        address.getTown(),
                        address.getCounty(),
                        address.getPostcode()
                ),
                updateUserRequest.getPhoneNumber(),
                updateUserRequest.getEmail(),
                existingUser.getCreatedAt(),
                Instant.ofEpochMilli(System.currentTimeMillis())
        );
    }

    public static Account accountFrom(String sortCode, Integer balance, Currency currency, User user, CreateBankAccountRequest createBankAccountRequest) {
        Instant now = Instant.ofEpochMilli(System.currentTimeMillis());

        return new Account(
                sortCode,
                user,
                createBankAccountRequest.getName(),
                AccountType.valueOf(createBankAccountRequest.getAccountType().name()),
                balance,
                currency,
                now,
                now
        );
    }

    public static Account accountFrom(Integer newBalance, Account account) {
        return new Account(
                account.getAccountNumber(),
                account.getSortCode(),
                account.getUser(),
                account.getName(),
                account.getType(),
                newBalance,
                account.getCurrency(),
                account.getCreatedAt(),
                Instant.ofEpochMilli(System.currentTimeMillis())
        );
    }

    public static Transaction transactionFrom(Account account, CreateTransactionRequest createTransactionRequest) {
        return new Transaction(
                Instant.ofEpochMilli(System.currentTimeMillis()),
                account,
                createTransactionRequest.getReference().orElse(null),
                TransactionType.valueOf(createTransactionRequest.getType().name()),
                Currency.valueOf(createTransactionRequest.getCurrency().name()),
                convertToMinorUnits(createTransactionRequest.getAmount())
        );
    }

    // ----- CREATE API RESPONSES

    public static UserResponse userResponseFrom(String message, User user) {
        Address address = user.getAddress();

        return new UserResponse(
                message,
                appendUserTag(user.getId()),
                user.getName(),
                new com.example.nishantpuria.bank.api.shared.Address(
                        address.getLine1(),
                        address.getLine2().orElse(null),
                        address.getLine3().orElse(null),
                        address.getTown(),
                        address.getCounty(),
                        address.getPostcode()
                ),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static BankAccountResponse bankAccountResponseFrom(String message, Account account) {
        return new BankAccountResponse(
                message,
                appendAccountNumberPrefix(account.getAccountNumber()),
                account.getSortCode(),
                account.getName(),
                com.example.nishantpuria.bank.api.shared.AccountType.valueOf(account.getType().name()),
                convertToMajorUnits(account.getBalance()),
                com.example.nishantpuria.bank.api.shared.Currency.valueOf(account.getCurrency().name()),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }

    public static ListBankAccountsResponse listBankAccountsResponseFrom(String message, List<BankAccountResponse> bankAccountResponses) {
        return new ListBankAccountsResponse(
                message,
                bankAccountResponses.toArray(new BankAccountResponse[0])
        );
    }

    public static TransactionResponse transactionResponseFrom(String message, Transaction transaction) {
        return new TransactionResponse(
                message,
                appendTransactionTag(transaction.getId()),
                convertToMajorUnits(transaction.getAmount()),
                com.example.nishantpuria.bank.api.shared.Currency.valueOf(transaction.getCurrency().name()),
                com.example.nishantpuria.bank.api.shared.TransactionType.valueOf(transaction.getType().name()),
                transaction.getReference().orElse(null),
                appendUserTag(transaction.getAccount().getUser().getId()),
                transaction.getCreatedAt()
        );
    }

    public static ListTransactionsResponse listTransactionsResponseFrom(String message, List<TransactionResponse> transactionResponses) {
        return new ListTransactionsResponse(
                message,
                transactionResponses.toArray(new TransactionResponse[0])
        );
    }

}