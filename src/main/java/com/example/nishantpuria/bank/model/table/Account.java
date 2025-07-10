package com.example.nishantpuria.bank.model.table;

import com.example.nishantpuria.bank.model.type.AccountType;
import com.example.nishantpuria.bank.model.type.Currency;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountNumber;

    @Column(nullable = false)
    private String sortCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType type;

    @Column(nullable = false)
    private Integer balance;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    private Account() {}

    public Account(String sortCode, User user, String name, AccountType type, Integer balance, Currency currency, Instant createdAt, Instant updatedAt) {
        this.sortCode = sortCode;
        this.user = user;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Account(Integer accountNumber, String sortCode, User user, String name, AccountType type, Integer balance, Currency currency, Instant createdAt, Instant updatedAt) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.user = user;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public Integer getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

}