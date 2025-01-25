package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class Payment {
    private static int counter = 0;
    private String paymentId;
    private double amount;
    private String method;

    public Payment( double amount, String method ) {
        this.paymentId = generatePaymentId();
        this.amount = amount;
        this.method = method;
    }

    public Payment(String paymentId, double amount, String method ) {
        counter++;
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
    }

    private String generatePaymentId() {
        counter++;
        return String.format(Locale.ENGLISH,"%08d", counter);
    }

    public String getPaymentId() {
        return paymentId;
    }


    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String toFileFormat() {
        return paymentId + "***" + amount + "***" + method;
    }

    public static Payment fromFileFormat(String str) {
        try {
            String[] parts = str.split("\\*\\*\\*");
            return new Payment(parts[0],  Double.parseDouble(parts[1]),parts[2]);
        } catch (Exception e) {
            System.out.println("There is a problem within the format of one of the payments while reading it from the file");

            return null;
        }
    }
}
