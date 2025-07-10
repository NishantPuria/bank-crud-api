package com.example.nishantpuria.bank.api.response;

import jakarta.validation.constraints.NotBlank;

public class ResponseObject {

    private final @NotBlank String message;

    public ResponseObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}