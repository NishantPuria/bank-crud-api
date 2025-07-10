package com.example.nishantpuria.bank.api.request;

import com.example.nishantpuria.bank.api.shared.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

// TODO: think about validation
public class UpdateUserRequest {

    private final @NotBlank String name;
    private final @Valid @NotNull Address address;
    private final @NotBlank @Pattern(regexp = "^\\+[1-9]\\d{1,14}$") String phoneNumber;
    private final @NotBlank @Pattern(regexp = "[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]+") String email;

    public UpdateUserRequest(
            String name,
            Address address,
            String phoneNumber,
            String email
    ) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

}