package Model;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Payments {
    private ArrayList<Payment> payments = new ArrayList<>();
    public static Object object =new Object();
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public Payment findPaymentById(String paymentId) {
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public  void writerThread(){
        Thread thread = new Thread(() -> saveToFile());
        thread.start();

    }
    public  void readerThread(){
        Thread thread = new Thread(() -> loadFromFile());
        thread.start();


    }

    public  void loadFromFile() {
        synchronized (payments) {
            try (BufferedReader br = new BufferedReader(new FileReader("Payments.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Payment payment = Payment.fromFileFormat(line);
                    if (payment != null) {
                        payments.add(payment);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Something went wrong  while reading from payments file ","Error",JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    public  void saveToFile() {
        synchronized (payments)
        {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Payments.txt"))) {
            for (Payment payment : payments) {
                bw.write(payment.toFileFormat());
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Something went wrong  while writing to payments file ","Error",JOptionPane.ERROR_MESSAGE);

        }
        }
    }

    public boolean checkTheCreditCard(String cardNumber) {
        if (cardNumber.matches("\\d{16}")) {
            return true;
        }
        return false;
    }
}
