package com.example.nishantpuria.bank.api;

import com.example.nishantpuria.bank.api.request.CreateBankAccountRequest;
import com.example.nishantpuria.bank.api.request.CreateTransactionRequest;
import com.example.nishantpuria.bank.api.request.UpdateBankAccountRequest;
import com.example.nishantpuria.bank.api.response.ResponseObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/accounts")
public interface AccountManagementInterface {

    @PostMapping("/{userId}")
    ResponseEntity<ResponseObject> createAccount(@PathVariable @Valid @NotBlank @Pattern(regexp = "^usr-[A-Za-z0-9]+$") String userId, @RequestBody @Valid @NotNull CreateBankAccountRequest createBankAccountRequest);

    @GetMapping("/user/{userId}")
    ResponseEntity<ResponseObject> listAccounts(@PathVariable @Valid @NotBlank @Pattern(regexp = "^usr-[A-Za-z0-9]+$") String userId);

    @GetMapping("/{accountNumber}")
    ResponseEntity<ResponseObject> fetchAccountByAccountNumber(@PathVariable @Valid @NotBlank @Pattern(regexp = "^01\\d{6}$") String accountNumber);

    @PatchMapping("/{accountNumber}")
    ResponseEntity<ResponseObject> updateAccountByAccountNumber(@PathVariable @Valid @NotBlank @Pattern(regexp = "^01\\d{6}$") String accountNumber, @RequestBody @Valid @NotNull UpdateBankAccountRequest updateBankAccountRequest);

    @DeleteMapping("/{accountNumber}")
    ResponseEntity<ResponseObject> deleteAccountByAccountNumber(@PathVariable @Valid @NotBlank @Pattern(regexp = "^01\\d{6}$") String accountNumber);

    @PostMapping("/{accountNumber}/transactions")
    ResponseEntity<ResponseObject> createTransaction(@PathVariable @Valid @NotBlank @Pattern(regexp = "^01\\d{6}$") String accountNumber, @RequestBody @Valid @NotNull CreateTransactionRequest createTransactionRequest);

    @GetMapping("/{accountNumber}/transactions")
    ResponseEntity<ResponseObject> listAccountTransactions(@PathVariable @Valid @NotBlank @Pattern(regexp = "^01\\d{6}$") String accountNumber);

    @GetMapping("/{accountNumber}/transactions/{transactionId}")
    ResponseEntity<ResponseObject> fetchAccountTransactionByID(@PathVariable @Valid @NotBlank @Pattern(regexp = "^01\\d{6}$") String accountNumber, @PathVariable @Valid @NotBlank @Pattern(regexp = "^tan-[A-Za-z0-9]+$") String transactionId);

}