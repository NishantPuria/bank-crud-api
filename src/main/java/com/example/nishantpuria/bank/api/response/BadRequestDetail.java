package com.example.nishantpuria.bank.api.response;

import jakarta.validation.constraints.NotBlank;

public class BadRequestDetail {

    private final @NotBlank String field;
    private final @NotBlank String message;
    private final @NotBlank String type;

    public BadRequestDetail(String field, String message, String type) {
        this.field = field;
        this.message = message;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

}