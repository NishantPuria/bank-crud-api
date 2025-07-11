package com.example.nishantpuria.bank.service;

import com.example.nishantpuria.bank.model.table.Transaction;
import com.example.nishantpuria.bank.persistence.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.nishantpuria.bank.utils.Formatter.removeAccountNumberPrefix;
import static com.example.nishantpuria.bank.utils.Formatter.removeTransactionTag;

@Component
public class TransactionManagementService {

    private final TransactionRepository transactionRepository;

    public TransactionManagementService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public Transaction findTransaction(String transactionId) {
        Integer id = removeTransactionTag(transactionId);
        return transactionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Transaction> findTransactionsByAccount(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        Integer id = removeAccountNumberPrefix(accountNumber);

        transactionRepository.findAll().forEach(transaction -> {
            if (transaction.getAccount().getAccountNumber().equals(id)) {
                transactions.add(transaction);
            }
        });

        return transactions;
    }

}