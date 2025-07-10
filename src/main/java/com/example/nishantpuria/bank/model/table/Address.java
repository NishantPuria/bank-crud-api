package com.example.nishantpuria.bank.model.table;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String line1;

    private String line2;

    private String line3;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String county;

    @Column(nullable = false)
    private String postcode;

    private Address() {}

    public Address(String line1, String line2, String line3, String town, String county, String postcode) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
    }

    public Integer getId() {
        return id;
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