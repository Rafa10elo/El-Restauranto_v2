package Model;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Report {
    private int numberOfOrders;
    private double totalMoney;
    private static Object object =new Object();
    private HashMap<Meal, Integer> orderedMeals;
    private HashMap<User, Integer> orderingUsers;

    public Report(int numberOfOrders, double totalMoney) {
        this.numberOfOrders = numberOfOrders;
        this.totalMoney = totalMoney;
        this.orderedMeals = new HashMap<>();
        this.orderingUsers = new HashMap<>();
    }

    public HashMap<User, Integer> getOrderingUsers() {
        return orderingUsers;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public  void incrementMealCount(Meal meal, int cnt) {
        orderedMeals.put(meal, orderedMeals.getOrDefault(meal, 0) + cnt);
    }

    public  void incrementUserCount(User user, int cnt) {
        orderingUsers.put(user, orderingUsers.getOrDefault(user, 0) + cnt);
    }

    public  void increaseNumberOfOrders() {
        numberOfOrders++;
    }

    public  void addToTotalMoney(double amount) {
        totalMoney += amount;
    }

    public Meal getMostSoldMeal() {
        List<Map.Entry<Meal, Integer>> sortedMeals = getSortedOrderedMeals();
        return sortedMeals.isEmpty() ? null : sortedMeals.get(0).getKey();
    }

    public User getMostOrderingUser() {
        List<Map.Entry<User, Integer>> sortedUsers = getSortedOrderingUsers();
        return sortedUsers.isEmpty() ? null : sortedUsers.get(0).getKey();
    }

    public List<Map.Entry<Meal, Integer>> getSortedOrderedMeals() {
        List<Map.Entry<Meal, Integer>> list = new ArrayList<>(orderedMeals.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
//        System.out.println("Sorted meals: " + list);
        return list;
    }

    public List<Map.Entry<User, Integer>> getSortedOrderingUsers() {
        List<Map.Entry<User, Integer>> list = new ArrayList<>(orderingUsers.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        return list;
    }

    public  void writerThread(){
        Thread thread = new Thread(() -> saveToFile());
        thread.start();

    }
    public  void readerThread(){
        Thread thread = new Thread(() -> loadFromFile());
        thread.start();


    }

    public void saveToFile() {
        synchronized (object) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reports.txt"))) {
                writer.write(String.valueOf(numberOfOrders));
                writer.newLine();
                writer.write(String.valueOf(totalMoney));
                writer.newLine();

                for (Map.Entry<Meal, Integer> entry : orderedMeals.entrySet()) {
                    writer.write(entry.getKey().toFileFormat() + "***" + entry.getValue());
                    writer.newLine();
                }
                writer.write("#####");
                writer.newLine();

                for (Map.Entry<User, Integer> entry : orderingUsers.entrySet()) {
                    writer.write(toFileFormat(entry.getKey()) + "=" + entry.getValue());
                    writer.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Something went wrong  while writing to report file ","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public  Report loadFromFile() {
        synchronized (object) {
            try (BufferedReader reader = new BufferedReader(new FileReader("Reports.txt"))) {
                int numberOfOrders = Integer.parseInt(reader.readLine());
                double totalMoney = Double.parseDouble(reader.readLine());
                Report report = new Report(numberOfOrders, totalMoney);

                String line;
                while (!(line = reader.readLine()).equals("#####")) {
                    try {
                        String[] parts = line.split("\\*\\*\\*");
                        if (parts.length != 2) {
                            continue;
                        }
                        Meal meal = Meal.fromFileFormat(parts[0]);
                        if (meal == null) {
                            continue;
                        }
                        int cnt = Integer.parseInt(parts[1]);
                        report.incrementMealCount(meal, cnt);
                    } catch (Exception e) {
                        System.out.println("Something went wrong  while reading some data within the report file ");
                    }
                }

                while ((line = reader.readLine()) != null) {
                    try {
                        String[] parts = line.split("\\=");
                        if (parts.length != 2) {
                            continue;
                        }
                        User user = fromFileFormat(parts[0]);
                        if (user == null) {
                            continue;
                        }
                        int cnt = Integer.parseInt(parts[1]);
                        report.incrementUserCount(user, cnt);
                    } catch (Exception e) {
                        System.out.println("something went wrong within the format of the report data");
                    }
                }
                return report;
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Something went wrong  while reading from report file ","Error",JOptionPane.ERROR_MESSAGE);
                return new Report(0, 0.0);
            }
        }
    }
    public User fromFileFormat(String str){
        String[] userParts = str.split("\\*\\*\\*");
        String userName = userParts[0];
        String email = userParts[1];
        String password = userParts[2];
        int userType= Integer.parseInt(userParts[3]);

        return new User(userName,email,password, userType);
    }
    public String toFileFormat(User user) {
        return user.getUserName() + "***" + user.getEmail() + "***" + user.getPassword() + "***" + user.getUserType() + "***";
    }


}
