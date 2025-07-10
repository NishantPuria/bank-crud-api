package com.example.nishantpuria.bank.controller;

import com.example.nishantpuria.bank.api.UserManagementInterface;
import com.example.nishantpuria.bank.api.request.CreateUserRequest;
import com.example.nishantpuria.bank.api.request.UpdateUserRequest;
import com.example.nishantpuria.bank.api.response.ResponseObject;
import com.example.nishantpuria.bank.api.response.UserResponse;
import com.example.nishantpuria.bank.model.table.User;
import com.example.nishantpuria.bank.persistence.AccountRepository;
import com.example.nishantpuria.bank.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.example.nishantpuria.bank.controller.utils.Formatter.removeUserTag;
import static com.example.nishantpuria.bank.controller.utils.Mapper.userFrom;
import static com.example.nishantpuria.bank.controller.utils.Mapper.userResponseFrom;
import static org.springframework.http.HttpStatus.*;

// TODO: add user authentication endpoint and update OpenAPI spec
// TODO: implement jwt authentication throughout
// TODO: refactor this class + add tests

@RestController
public class UserManagementController implements UserManagementInterface, InputValidator {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserManagementController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> createUser(CreateUserRequest createUserRequest) {
        try {
            User savedUser = userRepository.save(userFrom(createUserRequest));
            UserResponse userResponse = userResponseFrom("User has been created successfully", savedUser);
            return new ResponseEntity<>(userResponse, CREATED);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> fetchUserById(String userId) {
        try {
            User user = userRepository.findById(removeUserTag(userId)).orElseThrow(IllegalArgumentException::new);
            UserResponse userResponse = userResponseFrom("The user details", user);
            return new ResponseEntity<>(userResponse, OK);
        } catch (IllegalArgumentException e) {
            ResponseObject errorResponse = new ResponseObject("User was not found");
            return new ResponseEntity<>(errorResponse, NOT_FOUND);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateUserByID(String userId, UpdateUserRequest updateUserRequest) {
        try {
            User existingUser = userRepository.findById(removeUserTag(userId)).orElseThrow(IllegalArgumentException::new);
            User savedUser = userRepository.save(userFrom(existingUser, updateUserRequest));
            UserResponse userResponse = userResponseFrom("The updated user details", savedUser);
            return new ResponseEntity<>(userResponse, OK);
        } catch (IllegalArgumentException e) {
            ResponseObject errorResponse = new ResponseObject("User was not found");
            return new ResponseEntity<>(errorResponse, NOT_FOUND);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteUserByID(String userId) {
        try {
            User user = userRepository.findById(removeUserTag(userId)).orElseThrow(IllegalArgumentException::new);
            accountRepository.findAll().forEach(account -> {
                if (account.getUser().getId().equals(user.getId())) {
                    throw new IllegalStateException();
                }
            });
            userRepository.deleteById(user.getId());
            ResponseObject response = new ResponseObject("The user has been deleted");
            return new ResponseEntity<>(response, NO_CONTENT);
        } catch (IllegalArgumentException e) {
            ResponseObject errorResponse = new ResponseObject("User was not found");
            return new ResponseEntity<>(errorResponse, NOT_FOUND);
        } catch (IllegalStateException e) {
            ResponseObject errorResponse = new ResponseObject("A user cannot be deleted when they are associated with a bank account");
            return new ResponseEntity<>(errorResponse, CONFLICT);
        } catch (Exception e) {
            ResponseObject errorResponse = new ResponseObject("An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
        }
    }

}