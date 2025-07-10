package com.example.nishantpuria.bank.persistence;

import com.example.nishantpuria.bank.model.table.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {}