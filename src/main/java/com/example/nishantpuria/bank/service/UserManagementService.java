package com.example.nishantpuria.bank.service;

import com.example.nishantpuria.bank.model.table.User;
import com.example.nishantpuria.bank.persistence.AccountRepository;
import com.example.nishantpuria.bank.persistence.UserRepository;
import org.springframework.stereotype.Component;

import static com.example.nishantpuria.bank.utils.Formatter.removeUserTag;

@Component
public class UserManagementService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserManagementService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUser(String userId) {
        Integer id = removeUserTag(userId);
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteUser(String userId) {
        Integer id = removeUserTag(userId);
        userRepository.deleteById(id);
    }

    public void checkUserExists(String userId) {
        findUser(userId);
    }

    public void checkUserHasNoBankAccounts(String userId) {
        Integer id = removeUserTag(userId);
        accountRepository.findAll().forEach(account -> {
            if (account.getUser().getId().equals(id)) {
                throw new IllegalStateException();
            }
        });
    }

}