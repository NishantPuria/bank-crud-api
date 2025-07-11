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
import com.example.nishantpuria.bank.service.AccountManagementService;
import com.example.nishantpuria.bank.service.TransactionManagementService;
import com.example.nishantpuria.bank.service.UserManagementService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.nishantpuria.bank.utils.Mapper.*;
import static org.springframework.http.HttpStatus.*;

// TODO: implement jwt authentication throughout
// TODO: refactor this class + add tests

@RestController
public class AccountManagementController implements AccountManagementInterface, InputValidator {

    private final UserManagementService userManagementService;
    private final AccountManagementService accountManagementService;
    private final TransactionManagementService transactionManagementService;
    private final String SORT_CODE;

    public AccountManagementController(UserManagementService userManagementService, AccountManagementService accountManagementService, TransactionManagementService transactionManagementService, Environment env) {
        this.userManagementService = userManagementService;
        this.accountManagementService = accountManagementService;
        this.transactionManagementService = transactionManagementService;
        this.SORT_CODE = env.getProperty("bank.sortcode");
    }

    @Override
    public ResponseEntity<ResponseObject> createAccount(String userId, CreateBankAccountRequest createBankAccountRequest) {
        try {
            // TODO: extract user id using bearer token instead of path variable
            User user = userManagementService.findUser(userId);
            Account account = accountFrom(SORT_CODE, 0, Currency.GBP, user, createBankAccountRequest);
            accountManagementService.saveAccount(account);
            BankAccountResponse bankAccountResponse = bankAccountResponseFrom("Bank Account has been created successfully", account);
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
            List<BankAccountResponse> bankAccountResponses = accountManagementService.findAccountsByUser(userId)
                    .stream()
                    .map(account -> bankAccountResponseFrom("", account))
                    .toList();
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
            Account account = accountManagementService.findAccount(accountNumber);
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
            Account account = accountManagementService.findAccount(accountNumber);
            accountManagementService.checkAccountHasSufficientFunds(account, createTransactionRequest.getAmount(), createTransactionRequest.getType());
            // TODO: do we need to enforce max account balance?
            // TODO: save transaction and update account balance in same transaction
            Transaction transaction = transactionFrom(account, createTransactionRequest);
            transactionManagementService.saveTransaction(transaction);
            Integer newBalance = accountManagementService.calculateNewBalance(account.getBalance(), createTransactionRequest.getAmount(), createTransactionRequest.getType());
            Account updatedAccount = accountFrom(newBalance, account);
            accountManagementService.saveAccount(updatedAccount);
            TransactionResponse transactionResponse = transactionResponseFrom("Transaction has been created successfully", transaction);
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
            accountManagementService.checkAccountExists(accountNumber);
            List<TransactionResponse> transactionResponses = transactionManagementService.findTransactionsByAccount(accountNumber)
                    .stream()
                    .map(transaction -> transactionResponseFrom("", transaction))
                    .toList();
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
            Transaction transaction = transactionManagementService.findTransaction(transactionId);
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