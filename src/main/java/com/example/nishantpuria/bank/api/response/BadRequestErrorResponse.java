package com.example.nishantpuria.bank.api.response;

import jakarta.validation.constraints.NotEmpty;

public class BadRequestErrorResponse extends ResponseObject {

    private final @NotEmpty BadRequestDetail[] details;

    public BadRequestErrorResponse(String message, BadRequestDetail[] details) {
        super(message);
        this.details = details;
    }

    public BadRequestDetail[] getDetails() {
        return details;
    }

}