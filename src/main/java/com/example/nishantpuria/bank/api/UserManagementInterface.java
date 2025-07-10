package com.example.nishantpuria.bank.api;

import com.example.nishantpuria.bank.api.request.CreateUserRequest;
import com.example.nishantpuria.bank.api.request.UpdateUserRequest;
import com.example.nishantpuria.bank.api.response.ResponseObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/users")
public interface UserManagementInterface {

    @PostMapping
    ResponseEntity<ResponseObject> createUser(@RequestBody @Valid @NotNull CreateUserRequest createUserRequest);

    @GetMapping("/{userId}")
    ResponseEntity<ResponseObject> fetchUserById(@PathVariable @Valid @NotBlank @Pattern(regexp = "^usr-[A-Za-z0-9]+$") String userId);

    @PatchMapping("/{userId}")
    ResponseEntity<ResponseObject> updateUserByID(@PathVariable @Valid @NotBlank @Pattern(regexp = "^usr-[A-Za-z0-9]+$") String userId, @RequestBody @Valid @NotNull UpdateUserRequest updateUserRequest);

    @DeleteMapping("/{userId}")
    ResponseEntity<ResponseObject> deleteUserByID(@PathVariable @Valid @NotBlank @Pattern(regexp = "^usr-[A-Za-z0-9]+$") String userId);

}