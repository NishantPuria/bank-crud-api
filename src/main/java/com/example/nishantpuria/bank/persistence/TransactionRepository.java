package com.example.nishantpuria.bank.persistence;

import com.example.nishantpuria.bank.model.table.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {}