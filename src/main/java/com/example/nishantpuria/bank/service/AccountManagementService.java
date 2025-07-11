package com.example.nishantpuria.bank.service;

import com.example.nishantpuria.bank.api.shared.TransactionType;
import com.example.nishantpuria.bank.model.table.Account;
import com.example.nishantpuria.bank.persistence.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.nishantpuria.bank.utils.Formatter.*;

@Component
public class AccountManagementService {

    private final AccountRepository accountRepository;

    public AccountManagementService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public Account findAccount(String accountNumber) {
        Integer id = removeAccountNumberPrefix(accountNumber);
        return accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Account> findAccountsByUser(String userId) {
        List<Account> accounts = new ArrayList<>();
        Integer id = removeUserTag(userId);

        accountRepository.findAll().forEach(account -> {
            if (account.getUser().getId().equals(id)) {
                accounts.add(account);
            }
        });

        return accounts;
    }

    public void checkAccountExists(String accountNumber) {
        findAccount(accountNumber);
    }

    public void checkAccountHasSufficientFunds(Account account, Double transactionAmount, TransactionType transactionType) {
        Integer transactionAmountMinorUnits = convertToMinorUnits(transactionAmount);
        if (transactionAmountMinorUnits > account.getBalance() && transactionType == TransactionType.WITHDRAWAL) {
            throw new IllegalStateException();
        }
    }

    public Integer calculateNewBalance(Integer balance, Double transactionAmount, TransactionType transactionType) {
        Integer transactionAmountMinorUnits = convertToMinorUnits(transactionAmount);
        return transactionType == TransactionType.WITHDRAWAL
                ? balance - transactionAmountMinorUnits
                : balance + transactionAmountMinorUnits;
    }

}