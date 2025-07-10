package com.example.nishantpuria.bank.api.response;

import com.example.nishantpuria.bank.api.shared.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;

public class UserResponse extends ResponseObject {

    private final @NotBlank @Pattern(regexp = "^usr-[A-Za-z0-9]+$") String id;
    private final @NotBlank String name;
    private final @Valid @NotNull Address address;
    private final @NotBlank @Pattern(regexp = "^\\+[1-9]\\d{1,14}$") String phoneNumber;
    private final @NotBlank @Pattern(regexp = "[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]+") String email;
    private final @NotNull Instant createdTimestamp;
    private final @NotNull Instant updatedTimestamp;

    public UserResponse(String message, String id, String name, Address address, String phoneNumber, String email, Instant createdTimestamp, Instant updatedTimestamp) {
        super(message);
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

}