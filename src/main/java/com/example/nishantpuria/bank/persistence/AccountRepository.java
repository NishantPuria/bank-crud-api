package com.example.nishantpuria.bank.persistence;

import com.example.nishantpuria.bank.model.table.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {}