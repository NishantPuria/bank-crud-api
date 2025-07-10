package com.example.nishantpuria.bank.controller;

import com.example.nishantpuria.bank.api.AccountManagementInterface;
import com.example.nishantpuria.bank.api.request.CreateBankAccountRequest;
import com.example.nishantpuria.bank.api.request.CreateTransactionRequest;
import com.example.nishantpuria.bank.api.request.UpdateBankAccountRequest;
import com.example.nishantpuria.bank.api.response.*;
import com.example.nishantpuria.bank.model.table.Account;
import com.example.nishantpuria.bank.model.table.Transaction;
import com.example.nishantpuria.bank.model.table.User;
import com.example.nishantpuria.bank.model.type.Currency;
import com.example.nishantpuria.bank.persistence.AccountRepository;
import com.example.nishantpuria.bank.persistence.TransactionRepository;
import com.example.nishantpuria.bank.persistence.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.nishantpuria.bank.controller.utils.Formatter.*;
import static com.example.nishantpuria.bank.controller.utils.Mapper.*;
import static org.springframework.http.HttpStatus.*;

// TODO: implement jwt authentication throughout
// TODO: refactor this class + add tests

@RestController
public class AccountManagementController implements AccountManagementInterface, InputValidator {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final String SORT_CODE;

    public AccountManagementController(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, Environment env) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

        SORT_CODE = env.getProperty("bank.sortcode");
    }

    @Override
    public ResponseEntity<ResponseObject> createAccount(String userId, CreateBankAccountRequest createBankAccountRequest) {
        try {
            // TODO: extract user id using bearer token instead of path variable
            User user = userRepository.findById(removeUserTag(userId)).orElseThrow();
            Account savedAccount = accountRepository.save(accountFrom(SORT_CODE, 0, Currency.GBP, user, createBankAccountRequest));
            BankAccountResponse bankAccountResponse = bankAccountResponseFrom("Bank Account has been created successfully", savedAccount);
            return new ResponseEntity<>(bankAccountResponse, CREATED);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> listAccounts(String userId) {
        try {
            // TODO: extract user id using bearer token instead of path variable
            List<BankAccountResponse> bankAccountResponses = new ArrayList<>();
            accountRepository.findAll().forEach(account -> {
                if (account.getUser().getId().equals(removeUserTag(userId))) {
                    bankAccountResponses.add(bankAccountResponseFrom("", account));
                }
            });
            ListBankAccountsResponse listBankAccountsResponse = listBankAccountsResponseFrom("The list of bank accounts", bankAccountResponses);
            return new ResponseEntity<>(listBankAccountsResponse, OK);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> fetchAccountByAccountNumber(String accountNumber) {
        try {
            Account account = accountRepository.findById(removeAccountNumberPrefix(accountNumber)).orElseThrow(IllegalArgumentException::new);
            BankAccountResponse bankAccountResponse = bankAccountResponseFrom("The bank account details", account);
            return new ResponseEntity<>(bankAccountResponse, OK);
        } catch (IllegalArgumentException e) {
            ResponseObject errorResponse = new ResponseObject("Bank account was not found");
            return new ResponseEntity<>(errorResponse, NOT_FOUND);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateAccountByAccountNumber(String accountNumber, UpdateBankAccountRequest updateBankAccountRequest) {
        // TODO
        // BankAccountResponse
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> deleteAccountByAccountNumber(String accountNumber) {
        // TODO
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> createTransaction(String accountNumber, CreateTransactionRequest createTransactionRequest) {
        try {
            Integer transactionAmount = convertToMinorUnits(createTransactionRequest.getAmount());
            Account account = accountRepository.findById(removeAccountNumberPrefix(accountNumber)).orElseThrow(IllegalArgumentException::new);
            if (transactionAmount > account.getBalance() && createTransactionRequest.getType() == com.example.nishantpuria.bank.api.shared.TransactionType.WITHDRAWAL) {
                throw new IllegalStateException();
            }
            // TODO: do we need to enforce max account balance?
            Integer newBalance = createTransactionRequest.getType() == com.example.nishantpuria.bank.api.shared.TransactionType.WITHDRAWAL
                    ? account.getBalance() - transactionAmount
                    : account.getBalance() + transactionAmount;
            // TODO: save transaction and update account balance in same transaction
            Transaction savedTransaction = transactionRepository.save(transactionFrom(account, createTransactionRequest));
            accountRepository.save(accountFrom(newBalance, account));
            TransactionResponse transactionResponse = transactionResponseFrom("Transaction has been created successfully", savedTransaction);
            return new ResponseEntity<>(transactionResponse, CREATED);
        } catch (IllegalArgumentException e) {
            ResponseObject errorResponse = new ResponseObject("Bank account was not found");
            return new ResponseEntity<>(errorResponse, NOT_FOUND);
        } catch (IllegalStateException e) {
            ResponseObject errorResponse = new ResponseObject("Insufficient funds to process transaction");
            return new ResponseEntity<>(errorResponse, UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> listAccountTransactions(String accountNumber) {
        try {
            Account account = accountRepository.findById(removeAccountNumberPrefix(accountNumber)).orElseThrow(IllegalArgumentException::new);
            List<TransactionResponse> transactionResponses = new ArrayList<>();
            transactionRepository.findAll().forEach(transaction -> {
                if (transaction.getAccount().getAccountNumber().equals(account.getAccountNumber())) {
                    transactionResponses.add(transactionResponseFrom("", transaction));
                }
            });
            ListTransactionsResponse listTransactionsResponse = listTransactionsResponseFrom("The list of transaction details", transactionResponses);
            return new ResponseEntity<>(listTransactionsResponse, OK);
        } catch (IllegalArgumentException e) {
            ResponseObject errorResponse = new ResponseObject("Bank account was not found");
            return new ResponseEntity<>(errorResponse, NOT_FOUND);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> fetchAccountTransactionByID(String accountNumber, String transactionId) {
        try {
            // TODO: match up account number with transaction id
            Transaction transaction = transactionRepository.findById(removeTransactionTag(transactionId)).orElseThrow(IllegalArgumentException::new);
            TransactionResponse transactionResponse = transactionResponseFrom("The transaction details", transaction);
            return new ResponseEntity<>(transactionResponse, OK);
        } catch (IllegalArgumentException e) {
            ResponseObject errorResponse = new ResponseObject("Transaction was not found");
            return new ResponseEntity<>(errorResponse, NOT_FOUND);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

}