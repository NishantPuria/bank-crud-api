package com.example.nishantpuria.bank.controller;

import com.example.nishantpuria.bank.api.response.BadRequestDetail;
import com.example.nishantpuria.bank.api.response.BadRequestErrorResponse;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public interface InputValidator {

    @ExceptionHandler(HandlerMethodValidationException.class)
    default ResponseEntity<BadRequestErrorResponse> handleValidationExceptions(HandlerMethodValidationException ex) {

        List<? extends MessageSourceResolvable> errors = ex.getAllErrors();

        BadRequestDetail[] details = new BadRequestDetail[errors.size()];
        AtomicInteger i = new AtomicInteger(0);

        errors.forEach((error) -> {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : "";
            String errorMessage = error.getDefaultMessage();
            String type = error.getClass().getSimpleName();

            details[i.get()] = new BadRequestDetail(fieldName, errorMessage, type);
            i.getAndIncrement();
        });

        BadRequestErrorResponse badRequestErrorResponse = new BadRequestErrorResponse("Incomplete or invalid details supplied", details);

        return new ResponseEntity<>(badRequestErrorResponse, BAD_REQUEST);
    }

}