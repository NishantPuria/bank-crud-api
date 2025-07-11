package com.example.nishantpuria.bank.utils;

import java.util.stream.IntStream;

public class Formatter {

    public static String appendUserTag(Integer id) {
        return "usr-" + id;
    }

    public static Integer removeUserTag(String userId) {
        return Integer.parseInt(userId.replace("usr-",""));
    }

    public static String appendTransactionTag(Integer id) {
        return "tan-" + id;
    }

    public static Integer removeTransactionTag(String transactionId) {
        return Integer.parseInt(transactionId.replace("tan-",""));
    }

    public static String appendAccountNumberPrefix(Integer id) {
        StringBuilder fullAccountNumber = new StringBuilder("01");
        String accountNumber = id.toString();
        IntStream.range(0, 6 - accountNumber.length()).forEach(i -> fullAccountNumber.append("0"));
        fullAccountNumber.append(accountNumber);

        return fullAccountNumber.toString();
    }

    public static Integer removeAccountNumberPrefix(String accountNumber) {
        return Integer.parseInt(accountNumber.substring(2));
    }

    public static Integer convertToMinorUnits(Double amount) {
        return (int) (amount * 100);
    }

    public static Double convertToMajorUnits(Integer amount) {
        return amount.doubleValue() / 100;
    }

}