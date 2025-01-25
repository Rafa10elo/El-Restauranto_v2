package Model;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private HashMap<Meal, Integer> meals;
    // -------------------------------------------------------------change float to double
    private float totalPrice = 0;
    private float tip;
    private Status state;
    private LocalDateTime timeOfDelivery;
    private String paymentId;
    private boolean notification;
    private String username;
    Boolean inRestaurant ;

    public enum Status {PREPARING, DELIVERED, CANCELED}

    public Order(HashMap<Meal, Integer> meals, float totalPrice, float tip, Status state,String username,Boolean inRestaurant) {
        this.meals = new HashMap<>(meals);
        this.tip = tip;
        this.state = state;
        this.totalPrice = totalPrice ;
        this.timeOfDelivery = null;
        this.paymentId = null;
        this.username = username;
        this.inRestaurant=inRestaurant;
    }

    public Order(HashMap<Meal, Integer> meals, float totalPrice, float tip, Status state,
                 LocalDateTime timeOfDelivery, String paymentId,String username,Boolean inRestaurant) {
        this.meals = new HashMap<>(meals);
        this.totalPrice = totalPrice;
        this.tip = tip;
        this.state = state;
        this.timeOfDelivery = timeOfDelivery;
        this.paymentId = paymentId;
        if(state== Status.DELIVERED||state == Status.CANCELED)
            notification=true;
        else
            notification=false;

        this.username = username;
        this.inRestaurant= inRestaurant;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<Meal, Integer> getMeals() {
        return meals;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean getNotification() {
        return notification;
    }

    public float getTip() {
        return tip;
    }

    //------------------------------------------------------------------------------------------
    private void calculateTotalPrice() {
        totalPrice = tip;
        for (Map.Entry<Meal, Integer> entry : meals.entrySet()) {
            totalPrice += entry.getKey().price * entry.getValue();
        }
    }

    public synchronized void setState(Status state) {
        this.state = state;
    }

    public LocalDateTime getTimeOfDelivery() {
        return this.timeOfDelivery;
    }

    public void setTimeOfDelivery(LocalDateTime timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Status getState() {
        return this.state;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getInRestaurant() {
        return inRestaurant;
    }

    //---------------------------------- tmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
    public void addMeal(Meal meal) {
        meals.put(meal, meals.getOrDefault(meal, 0) + 1);
        calculateTotalPrice();
    }

    //---------------------------------------tmmmmmmmmmmmmmmmmmmmmm
    public boolean removeMeal(Meal meal) {
        if (meals.containsKey(meal)) {
            meals.remove(meal);
            calculateTotalPrice();
            return true;
        }
        return false;
    }

    //------------------------------------
    public boolean modifyMealCnt(Meal meal, int cnt) {
        if (meals.containsKey(meal)) {
            if (cnt > 0) {
                meals.put(meal, cnt);
            } else {
                meals.remove(meal);
            }
            calculateTotalPrice();
            return true;
        }
        return false;
    }

    public String toFileFormat() {
        String orderString = "";

        for (Map.Entry<Meal, Integer> entry : meals.entrySet()) {
            orderString += entry.getKey().toFileFormat() + "##_" + entry.getValue() + "||";
        }

        orderString += "@@" + this.totalPrice;
        orderString += "@@" + this.tip;
        orderString += "@@" + this.state.name();



        if (this.timeOfDelivery != null) {
            orderString += "@@" + this.timeOfDelivery.toString();
        } else {
            orderString += "@@null";
        }

        if (this.paymentId != null) {
            orderString += "@@" + this.paymentId;
        } else {
            orderString += "@@null";
        }
        orderString += "@@" + this.username;
        orderString += "@@" + this.inRestaurant;

        return orderString;
    }

    public static Order fromFileFormat(String str) {
        try {
            String[] orderParts = str.split("@@");
            String[] mealStrings = orderParts[0].split("\\|\\|");
            HashMap<Meal, Integer> meals = new HashMap<>();

            for (String mealStr : mealStrings) {
                if (!mealStr.isEmpty()) {
                    String[] mealData = mealStr.split("##_");
                    Meal meal = Meal.fromFileFormat(mealData[0]);
                    int count = Integer.parseInt(mealData[1]);
                    if (meal != null)
                        meals.put(meal, count);
                }
            }

            float totalPrice = Float.parseFloat(orderParts[1]);
            float tip = Float.parseFloat(orderParts[2]);
            Status state = Status.valueOf(orderParts[3]);
            LocalDateTime timeOfDelivery = null;

            if (!orderParts[4].equals("null")) {
                timeOfDelivery = LocalDateTime.parse(orderParts[4]);
            }

            String paymentId = null;
            if (!orderParts[5].equals("null")) {
                paymentId = orderParts[5];
            }
            String username = orderParts[6];
            boolean inRestaurant = Boolean.parseBoolean(orderParts[7]);
            

            return new Order(meals, totalPrice, tip, state, timeOfDelivery, paymentId,username,inRestaurant);

        } catch (Exception e) {
            System.out.println("There is a problem within the format of one of the orders while reading it from the file");
            return null;
        }
    }
}
