package com.example.nishantpuria.bank.model.table;

import com.example.nishantpuria.bank.model.type.Currency;
import com.example.nishantpuria.bank.model.type.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;
import java.util.Optional;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;

    private String reference;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    private Account account;

    @Column(nullable = false)
    private Instant createdAt;

    private Transaction() {}

    public Transaction(Instant createdAt, Account account, String reference, TransactionType type, Currency currency, Integer amount) {
        this.createdAt = createdAt;
        this.account = account;
        this.reference = reference;
        this.type = type;
        this.currency = currency;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAmount() {
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

    public Account getAccount() {
        return account;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}