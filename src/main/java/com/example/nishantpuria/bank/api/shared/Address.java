package com.example.nishantpuria.bank.api.shared;

import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public class Address {

    private final @NotBlank String line1;
    private final String line2;
    private final String line3;
    private final @NotBlank String town;
    private final @NotBlank String county;
    private final @NotBlank String postcode;

    public Address(
            String line1,
            String line2,
            String line3,
            String town,
            String county,
            String postcode
    ) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
    }

    public String getLine1() {
        return line1;
    }

    public Optional<String> getLine2() {
        return Optional.ofNullable(line2);
    }

    public Optional<String> getLine3() {
        return Optional.ofNullable(line3);
    }

    public String getTown() {
        return town;
    }

    public String getCounty() {
        return county;
    }

    public String getPostcode() {
        return postcode;
    }

}